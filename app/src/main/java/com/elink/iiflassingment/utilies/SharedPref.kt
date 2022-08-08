package com.elink.iiflassingment.utilies

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Nitin Sabale
 * @since 27-07-2022. 12:25
 * this object file use for to store session data using shared preferences
 */
object SharedPref {
    private var mSharedPref: SharedPreferences? = null
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"
     const val DARK_STATUS = "DARK_STATUS"
    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE)
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.commit()
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return mSharedPref!!.getBoolean(key, defValue)
    }

    fun setStatus(key: String?, value: Int) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(DARK_STATUS, value)
        prefsEditor.commit()
    }

    fun readStatus(key: String?, defValue: Int): Int {
        return mSharedPref!!.getInt(key, defValue)
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putInt(key, value!!).commit()
    }


}