package com.example.bitcotappointment.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bitcotappointment.databinding.ActivityLoginBinding
import com.example.bitcotappointment.ui.blueprints.BaseAppCompatActivity
import com.example.bitcotappointment.ui.doctorhome.DoctorHomeActivity
import com.example.bitcotappointment.ui.patienthome.PatientHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseAppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null

    private val mBinding get() = _binding!!

    private val mViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        addOnClickListener()

        addDataObserver()

    }

    private fun addOnClickListener(){

        mBinding.actionbtnLogin.setOnClickListener {

            mViewModel.login(email = mBinding.inputEmail.text.toString(), password = mBinding.inputPassword.text.toString())

        }

    }

    private fun addDataObserver(){

        mViewModel.isLoading.observe(this){

            displayLoadingProgress(show = it)

        }

        mViewModel.doctorLoginSuccess.observe(this){

            if(it) {

                startActivity(DoctorHomeActivity.getIntent(this))

                finish()

            }

        }

        mViewModel.patientLoginSuccess.observe(this){

            if(it) {

                startActivity(PatientHomeActivity.getIntent(this))

                finish()

            }

        }

        mViewModel.feedbackMessage.observe(this){ msg ->

            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

        }

    }
}