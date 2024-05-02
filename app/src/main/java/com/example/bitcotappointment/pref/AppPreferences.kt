package com.example.bitcotappointment.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.bitcotappointment.R
import javax.inject.Inject

class AppPreferences @Inject constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_key),
        Context.MODE_PRIVATE
    )

    private val keyIsInitialDataSaved = "IsInitialDataSaved"

    private val keyUserName = "UserName"

    private val keyUserID = "UserID"

    var IsInitialDataSaved: Boolean
        set(value) = putBoolean(key = keyIsInitialDataSaved, value = value)
        get() = getBoolean(key = keyIsInitialDataSaved)

    var UserName: String
        set(value) = putString(key = keyUserName, value = value)
        get() = getString(key = keyUserName)

    var UserID: Int
        set(value) = putInt(key = keyUserID, value = value)
        get() = getInt(key = keyUserID)


    private fun putString(key: String, value: String) {

        with(sharedPreferences.edit()) {

            putString(key, value)

            apply()

        }

    }

    private fun getString(key: String): String {

        return sharedPreferences.getString(key, "") ?: ""

    }

    private fun putInt(key: String, value: Int) {

        with(sharedPreferences.edit()) {

            putInt(key, value)

            apply()

        }

    }

    private fun getInt(key: String): Int {

        return sharedPreferences.getInt(key, 0)

    }

    private fun putBoolean(key: String, value: Boolean) {

        with(sharedPreferences.edit()) {

            putBoolean(key, value)

            apply()

        }

    }

    private fun getBoolean(key: String): Boolean {

        return sharedPreferences.getBoolean(key, false)

    }

}