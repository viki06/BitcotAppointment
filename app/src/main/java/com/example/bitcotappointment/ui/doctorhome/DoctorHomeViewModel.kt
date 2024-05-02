package com.example.bitcotappointment.ui.doctorhome

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
class DoctorHomeViewModel @Inject constructor(
    private val repository: Repository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading get() = _isLoading

    private val _appointmentList: MutableLiveData<List<DBModels.AppointmentWithPatientDetails>> by lazy { MutableLiveData() }
    val appointmentList get() = _appointmentList

    private val _feedbackMessage: MutableLiveData<String> by lazy { MutableLiveData() }
    val feedbackMessage get() = _feedbackMessage

    fun getAppointments() {

        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            try {

                _appointmentList.postValue(repository.getAppointmentsForDoctor(appPreferences.UserID))

                _isLoading.postValue(false)

            } catch (e: Exception) {

                _isLoading.postValue(false)

                e.printStackTrace()

            }

        }

    }

    fun updateAppointment(
        status: DBModels.AppointmentStatus,
        appointment: DBModels.Appointment,
        comments: String = AppConstants.EMPTY_STRING,
    ) {

        _isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            try {

                 repository.updateAppointment(
                    appointment.apply {
                        this.status = status
                        this.comments = comments
                    }
                 )

                _isLoading.postValue(false)

                getAppointments()


            } catch (e: Exception) {

                _isLoading.postValue(false)

                _feedbackMessage.postValue(AppConstants.PLEASE_TRY_AGAIN)

                e.printStackTrace()

            }

        }

    }


}