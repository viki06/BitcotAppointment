package com.example.bitcotappointment.ui.doctorhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcotappointment.R
import com.example.bitcotappointment.databinding.ItemAppointmentBinding
import com.example.bitcotappointment.model.DBModels

class AppointmentsAdapter : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>(){

    private var appointmentList = listOf<DBModels.AppointmentWithPatientDetails>()

    private var mListener : OnAppointmentStatusChangeListener? = null

    fun setData(appointmentList: List<DBModels.AppointmentWithPatientDetails>){

        this.appointmentList = appointmentList

        notifyDataSetChanged()

    }

    class ViewHolder(val binding: ItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAppointmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = appointmentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        appointmentList[position].also { appointment ->

            holder.binding.also { binding ->

                binding.txtPatientName.text = appointment.patient.name

                binding.txtPatientAge.text = holder.itemView.context.getString(R.string.prefix_age, appointment.patient.age)

                binding.txtAppointmentStatus.text = holder.itemView.context.getString(R.string.prefix_status, appointment.appointment.status.string)

                binding.txtAppointmentNumber.text = holder.itemView.context.getString(R.string.prefix_appointment, appointment.appointment.appointmentId)

                when(appointment.appointment.status){

                    DBModels.AppointmentStatus.PENDING -> {

                        binding.flowActionButton.visibility = View.VISIBLE

                        binding.actionCompleteAppoinment.visibility = View.GONE

                    }
                    DBModels.AppointmentStatus.ACCEPTED -> {

                        binding.flowActionButton.visibility = View.GONE

                        binding.actionCompleteAppoinment.visibility = View.VISIBLE

                    }

                    DBModels.AppointmentStatus.REJECTED ,
                    DBModels.AppointmentStatus.COMPLETED -> {

                        binding.flowActionButton.visibility = View.GONE

                        binding.actionCompleteAppoinment.visibility = View.GONE

                    }

                }

                binding.actionAcceptAppoinment.setOnClickListener { mListener?.onAppointmentStatusChange(DBModels.AppointmentStatus.ACCEPTED, appointment.appointment) }

                binding.actionRejectAppoinment.setOnClickListener {  mListener?.onAppointmentStatusChange(DBModels.AppointmentStatus.REJECTED, appointment.appointment) }

                binding.actionCompleteAppoinment.setOnClickListener { mListener?.onAppointmentStatusChange(DBModels.AppointmentStatus.COMPLETED, appointment.appointment) }

            }

        }

    }

    fun setListener(listener: OnAppointmentStatusChangeListener) {

        mListener = listener

    }

    interface OnAppointmentStatusChangeListener{

        fun onAppointmentStatusChange(
            status: DBModels.AppointmentStatus,
            appointment: DBModels.Appointment
        )


    }

}