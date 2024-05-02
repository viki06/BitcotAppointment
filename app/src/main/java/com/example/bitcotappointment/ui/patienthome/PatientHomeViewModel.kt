package com.example.bitcotappointment.ui.patienthome

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
class PatientHomeViewModel @Inject constructor(
    private val repository: Repository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading get() = _isLoading

    private val _doctorList: MutableLiveData<List<DBModels.User>> by lazy { MutableLiveData() }
    val doctorList get() = _doctorList

    private val _feedbackMessage: MutableLiveData<String> by lazy { MutableLiveData() }
    val feedbackMessage get() = _feedbackMessage

    fun getDoctorList() {

        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            try {

                _doctorList.postValue(repository.getUsersByType(DBModels.UserType.DOCTOR))

                _isLoading.postValue(false)

            } catch (e: Exception) {

                _isLoading.postValue(false)

                e.printStackTrace()

            }

        }

    }

    fun insertAppointment(doctorID: Int) {

        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            try {

                repository.insertAppointment(
                    DBModels.Appointment(
                        patientId = appPreferences.UserID,
                        doctorId = doctorID,
                        status = DBModels.AppointmentStatus.PENDING,
                        comments = AppConstants.EMPTY_STRING
                    )
                )

                _isLoading.postValue(false)

                _feedbackMessage.postValue(AppConstants.APPOINTMENT_ADDED)


            } catch (e: Exception) {

                _isLoading.postValue(false)

                _feedbackMessage.postValue(AppConstants.PLEASE_TRY_AGAIN)

                e.printStackTrace()

            }

        }

    }

}