package com.elink.iiflassingment.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.*
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.elink.iiflassingment.MainActivity
import com.elink.iiflassingment.R
import com.elink.iiflassingment.utilies.SharedPref
import com.elink.iiflassingment.utilies.Utility
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    
    // variable for email and password.
    var email = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SharedPref.init(getApplicationContext());

        // our login button with id.
        email = SharedPref.read(SharedPref.EMAIL_KEY, email).toString()//save string in shared preference.
        password = SharedPref.read(SharedPref.PWD_KEY, password).toString();//save int in shared preference.

        btnLogin.setOnClickListener(this)
        btnLogin.isEnabled = false
        btnLogin.setBackgroundColor(Color.GRAY)
        // on text change listner action for validation login button enable
        onTextChangeListener()
    }


    private fun onTextChangeListener() {

        editTextEmailID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (inputTextValidation()) {
                    btnLogin.isEnabled = true
                    btnLogin.setBackgroundColor(ContextCompat.getColor(applicationContext!!,
                        R.color.teal_200
                    ))
                } else {
                    btnLogin.isEnabled = false
                    btnLogin.setBackgroundColor(Color.GRAY)
                }

            }

        })
        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (inputTextValidation()) {
                    btnLogin.isEnabled = true
                    btnLogin.setBackgroundColor(ContextCompat.getColor(applicationContext!!,
                        R.color.teal_200
                    ))
                } else {
                    btnLogin.isEnabled = false
                    btnLogin.setBackgroundColor(Color.GRAY)
                }
            }

        })
    }
    // in this method start to next activity
    private fun nextToActivity() {
        SharedPref.write(SharedPref.EMAIL_KEY, editTextEmailID.text.toString());//save string in shared preference.
        SharedPref.write(SharedPref.PWD_KEY, editTextPassword.text.toString());//save int in shared preference.
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }
    // in this method we are checking validation.
    private fun validation(): Boolean {
        var isValid = false
        if (editTextEmailID.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.please_enter_email),
                    Toast.LENGTH_SHORT).show()
        } else if (!Utility.isValidString(editTextEmailID.text.toString())) {
            Toast.makeText(applicationContext, getString(R.string.please_enter_valid_email_address),
                    Toast.LENGTH_SHORT).show()
        } else if (editTextPassword.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, getString(R.string.please_enter_password),
                    Toast.LENGTH_SHORT).show()
        } else if (editTextPassword.text.toString().length < 8) {
            Toast.makeText(applicationContext, getString(R.string.please_enter_valid_password),
                    Toast.LENGTH_SHORT).show()
        } else {
            isValid = true
        }
        return isValid
    }
    // in this method we are checking validation for email id and password validation
    private fun inputTextValidation(): Boolean {
        var isValid = false
        if (editTextEmailID.text.toString().length > 0 && editTextPassword.text.toString().length > 0) {
            isValid = true
        }
        return isValid
    }


    // on below line we are calling on start method.
    override fun onStart() {
        super.onStart()
        email = SharedPref.read(SharedPref.EMAIL_KEY, email).toString()//save string in shared preference.
        password = SharedPref.read(SharedPref.PWD_KEY, password).toString();//save int in shared preference.

        // in this method we are checking if email and password are not empty.
        if (!email.equals("") && !password.equals("")) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                    if (validation()) {
                        // starting our activity.
                        nextToActivity()
                    }
            }
        }
    }

}