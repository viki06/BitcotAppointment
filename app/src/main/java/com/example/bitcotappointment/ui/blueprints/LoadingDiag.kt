package com.example.bitcotappointment.ui.blueprints

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.bitcotappointment.R

class LoadingDiag (context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window?.attributes?.windowAnimations = R.style.LoadingDialogAnimation

        window?.setBackgroundDrawableResource(android.R.color.transparent)

        setCancelable(false)

        setCanceledOnTouchOutside(false)

        setContentView(R.layout.loading_diag)

    }

    fun load() {

        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )

        show()

        if (hasSoftButtons()) hideSystemUI()

        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {

        super.onWindowFocusChanged(hasFocus)

        if (hasFocus)

            if (hasSoftButtons()) hideSystemUI()

    }

    private fun hideSystemUI() {

        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

    }

    private fun hasSoftButtons() = !KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

}