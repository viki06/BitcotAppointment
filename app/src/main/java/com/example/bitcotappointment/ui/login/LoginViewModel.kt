package com.example.bitcotappointment.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcotappointment.app.AppConstants
import com.example.bitcotappointment.model.DBModels
import com.example.bitcotappointment.pref.AppPreferences
import com.example.bitcotappointment.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: Repository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _patientLoginSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val patientLoginSuccess get() = _patientLoginSuccess

    private val _doctorLoginSuccess: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val doctorLoginSuccess get() = _doctorLoginSuccess

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading get() = _isLoading

    private val _feedbackMessage: MutableLiveData<String> by lazy { MutableLiveData() }
    val feedbackMessage get() = _feedbackMessage

    init {

        if(!appPreferences.IsInitialDataSaved){

            userRepository.initDatabase()

            appPreferences.IsInitialDataSaved = true

        }

    }

    fun login(email: String, password: String) {

        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            try {

                processResponse(user = userRepository.login(email, password))

            } catch (e: Exception) {

                e.printStackTrace()

                processError()

            }

        }

    }

    private fun processError() {

        _isLoading.postValue(false)

        _feedbackMessage.postValue(AppConstants.INTERNAL_ISSUES)

    }

    private fun processResponse(user: DBModels.User?) {

        _isLoading.postValue(false)

        if (user != null) {

            appPreferences.UserID = user.userId

            appPreferences.UserName = user.name

            when (user.type) {

                DBModels.UserType.DOCTOR -> _doctorLoginSuccess.postValue(true)

                DBModels.UserType.PATIENT -> _patientLoginSuccess.postValue(true)

            }

            _feedbackMessage.postValue(AppConstants.LOGIN_SUCCESS)

        } else {

            _feedbackMessage.postValue(AppConstants.INVALID_USER)

        }

    }

}