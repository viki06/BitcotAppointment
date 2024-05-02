package com.example.bitcotappointment.ui.doctorhome


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.bitcotappointment.R
import com.example.bitcotappointment.databinding.DialogAddCommentBinding

class AddCommentDialog : DialogFragment() {

    private var _binding : DialogAddCommentBinding? = null

    private val mBinding get() = _binding!!

    private var mListener : OnCommentsAddedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomDialogTheme)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)

        _binding = DialogAddCommentBinding.inflate(inflater, container,false)

        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mBinding.actionCancel.setOnClickListener {

            dismiss()

        }

        mBinding.actionComplete.setOnClickListener {

            mListener?.onCommentsAdded(mBinding.inputComment.text.toString())

            dismiss()

        }

    }

    fun setListener(listener: OnCommentsAddedListener){

        mListener = listener

    }

    interface OnCommentsAddedListener{

        fun onCommentsAdded(comments : String)

    }

    companion object{

        const val TAG_ADD_COMMENT = "TAG_ADD_COMMENT"

    }

}