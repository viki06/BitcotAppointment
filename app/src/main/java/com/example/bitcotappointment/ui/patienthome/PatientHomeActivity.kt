package com.example.bitcotappointment.ui.patienthome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bitcotappointment.databinding.ActivityPatientHomeBinding
import com.example.bitcotappointment.ui.blueprints.BaseAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeActivity : BaseAppCompatActivity() {

    private var _binding : ActivityPatientHomeBinding? = null

    private val mBinding get() = _binding!!

    private val mViewModel : PatientHomeViewModel by viewModels()

    private lateinit var mAdapter : DoctorListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = ActivityPatientHomeBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mAdapter = DoctorListAdapter()

        mBinding.recyclerView.adapter = mAdapter

        mBinding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                DividerItemDecoration.VERTICAL
            )
        )

        mAdapter.setListener(object : DoctorListAdapter.OnBookAppointmentListener{

            override fun onBookAppointment(doctorID: Int) {

                mViewModel.insertAppointment(doctorID)

            }

        })

        addDataObserver()

        mViewModel.getDoctorList()

    }

    private fun addDataObserver(){

        mViewModel.isLoading.observe(this){

            displayLoadingProgress(show = it)

        }

        mViewModel.doctorList.observe(this){

            mAdapter.setData(it)

        }

        mViewModel.feedbackMessage.observe(this){ msg ->

            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()

        }

    }

    companion object{

        fun getIntent(context: Context) : Intent = Intent(context, PatientHomeActivity::class.java)

    }

}