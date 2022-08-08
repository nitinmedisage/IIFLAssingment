package com.elink.iiflassingment.utilies

import android.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import java.io.IOException
import java.util.regex.Pattern
import kotlin.math.ln
import kotlin.math.pow

/**
 * Created by Nitin Sabale
 * @since 27-07-2022. 16:59
 * this is common helper class
 */
object Utility {

    // validate email pattern
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    // in this method we are checking email validation.
    fun isValidString(toString: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(toString).matches()
    }
    fun showAlertMessage(context: Context, message: String, okClick: () -> Unit) {
        AlertDialog.Builder(context).setTitle(context.getString(com.elink.iiflassingment.R.string.alert))
            .setMessage(message)
            .setPositiveButton(context.resources.getString(R.string.ok)) { dialogInterface, i ->
                dialogInterface.dismiss()
                okClick()
            }.create().show()
    }
    /**
     *  this method use for to get data from json file
     *  @param context
     *  @param fileName
     */
    fun getJsonFromAssets(context: Context, fileName: String?): String? {
        val jsonString: String
        jsonString = try {
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
    /**
     *  this method use for to set price in KLC formate
     *  @param str
     */
    fun getFormatedNumber(str: String): String {
        if (!str.equals("") || !str.isNullOrBlank()) {
            val num: String
            val count: Long
            if (str.indexOf('.') !== -1) {
                val countDouble = str.toDouble()
                count = countDouble.toLong()
            } else {
                count = str.toLong()
            }
            if (count < 1000) return "" + count
            val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
            num = String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "KLCTPE"[exp - 0])
            return num
        } else return ""
    }
    /**
     *  this method use for to choose your theme
     *  @param context
     *  @param applyDayNight
     */
    fun chooseThemeDialog(context: Context, applyDayNight: Boolean) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(com.elink.iiflassingment.R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = SharedPref.readStatus(SharedPref.DARK_STATUS,0)

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SharedPref.setStatus(SharedPref.DARK_STATUS,0)
                    applyDayNight
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    SharedPref.setStatus(SharedPref.DARK_STATUS,1)
                    applyDayNight
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    SharedPref.setStatus(SharedPref.DARK_STATUS,2)
                    applyDayNight
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }
    /**
     *  this method use for to check seleted theme using shared preferences class storing data
     *  @param applyDayNight
     */
    fun checkTheme(applyDayNight: Boolean) {
        when ( SharedPref.readStatus(SharedPref.DARK_STATUS,0)) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                applyDayNight
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                applyDayNight
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                applyDayNight
            }
        }
    }

}
