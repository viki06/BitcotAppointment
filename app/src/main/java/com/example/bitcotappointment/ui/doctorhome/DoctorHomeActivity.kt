package com.example.bitcotappointment.ui.doctorhome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bitcotappointment.databinding.ActivityDoctorHomeBinding
import com.example.bitcotappointment.model.DBModels
import com.example.bitcotappointment.ui.blueprints.BaseAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorHomeActivity : BaseAppCompatActivity() {

    private var _binding : ActivityDoctorHomeBinding? = null

    private val mBinding get() = _binding!!

    private val mViewModel : DoctorHomeViewModel by viewModels()

    private lateinit var mAdapter : AppointmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = ActivityDoctorHomeBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mAdapter = AppointmentsAdapter()

        mBinding.recyclerView.adapter = mAdapter

        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )

        mAdapter.setListener(object : AppointmentsAdapter.OnAppointmentStatusChangeListener{

            override fun onAppointmentStatusChange(
                status: DBModels.AppointmentStatus,
                appointment: DBModels.Appointment
            ) {

                when(status){

                    DBModels.AppointmentStatus.PENDING -> {}
                    DBModels.AppointmentStatus.ACCEPTED,
                    DBModels.AppointmentStatus.REJECTED -> {

                        mViewModel.updateAppointment(status, appointment)

                    }
                    DBModels.AppointmentStatus.COMPLETED -> {

                        val dialogFragment = AddCommentDialog()

                        dialogFragment.show(supportFragmentManager, AddCommentDialog.TAG_ADD_COMMENT)

                        dialogFragment.setListener(object : AddCommentDialog.OnCommentsAddedListener{

                            override fun onCommentsAdded(comments: String) {

                                mViewModel.updateAppointment(status, appointment, comments)

                            }

                        })

                    }
                }

            }

        })

        addDataObserver()

        mViewModel.getAppointments()

    }


    private fun addDataObserver(){

        mViewModel.isLoading.observe(this){

            displayLoadingProgress(show = it)

        }

        mViewModel.appointmentList.observe(this){

            mAdapter.setData(it)

        }

        mViewModel.feedbackMessage.observe(this){ msg ->

            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()

        }

    }

    companion object{

        fun getIntent(context: Context) : Intent = Intent(context, DoctorHomeActivity::class.java)

    }
}