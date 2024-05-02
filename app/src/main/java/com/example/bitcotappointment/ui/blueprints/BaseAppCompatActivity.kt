package com.example.bitcotappointment.ui.blueprints

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

open class BaseAppCompatActivity : AppCompatActivity() {

    private lateinit var mContentLoadingDiag: LoadingDiag

    protected fun displayLoadingProgress(show: Boolean) {

        if (!this::mContentLoadingDiag.isInitialized) {

            mContentLoadingDiag = LoadingDiag(this as Activity)

            mContentLoadingDiag.setCancelable(false)

            mContentLoadingDiag.setCanceledOnTouchOutside(false)

        }

        if (show)
            mContentLoadingDiag.load()
         else
            mContentLoadingDiag.dismiss()

    }

}