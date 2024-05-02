package com.example.bitcotappointment.ui.patienthome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcotappointment.R
import com.example.bitcotappointment.databinding.ItemDoctorBinding
import com.example.bitcotappointment.model.DBModels

class DoctorListAdapter : RecyclerView.Adapter<DoctorListAdapter.ViewHolder>() {

    private var doctorList = listOf<DBModels.User>()

    private var mListener : OnBookAppointmentListener? = null

    fun setData(doctorList: List<DBModels.User>){

        this.doctorList = doctorList

        notifyDataSetChanged()

    }

    class ViewHolder(val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = doctorList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        doctorList[position].also { user ->

            holder.binding.also { binding ->

                binding.txtDoctorName.text = user.name

                binding.txtDoctorAge.text = holder.itemView.context.getString(R.string.prefix_age,user.age)

                binding.actionBookAppoinment.setOnClickListener {

                    mListener?.onBookAppointment(user.userId)

                }

            }

        }

    }

    fun setListener(listener: OnBookAppointmentListener){

        mListener = listener

    }

    interface OnBookAppointmentListener{

        fun onBookAppointment(doctorID: Int)

    }

}