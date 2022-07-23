package com.securoserv.ui.login

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.securoserv.MainActivity
import com.securoserv.R
import com.securoserv.ui.signup.SignUpActivity
import com.securoserv.utils.hideSoftKeyboard

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var inputLayoutPassword: TextInputLayout
    lateinit var btnLogin: MaterialButton
    lateinit var signUpLayout: LinearLayout

    var email: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = this.findViewById(R.id.et_email)
        etPassword = this.findViewById(R.id.et_password)
        inputLayoutPassword = this.findViewById(R.id.input_layout_password)
        btnLogin = this.findViewById(R.id.btn_login)
        signUpLayout = this.findViewById(R.id.layout_signup)

        actions()
    }

    private fun actions() {

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etEmail.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))
                email = p0.toString()
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    inputLayoutPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.MULTIPLY)
                    password = p0.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        etPassword.setOnEditorActionListener { textView, i, keyEvent ->
            val action = i and EditorInfo.IME_MASK_ACTION
            when (action) {
                EditorInfo.IME_ACTION_DONE -> {
                    try {
                        if (checkValidation(
                                etEmail.text.toString().trim(),
                                etPassword.text.toString().trim()
                            )
                        ) {
                            email = etEmail.text.toString().trim()
                            password = etPassword.text.toString().trim()
//                            if (this.isNetworkActiveWithMessage())
//                                loginApi()
                        }
                        hideSoftKeyboard(this)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
            true
        }

        btnLogin.setOnClickListener {
            if (checkValidation(email, password)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        signUpLayout.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.error_email)
            etEmail.requestFocus()
            return false

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = getString(R.string.error_invalid_email)
            etEmail.requestFocus()
            return false

        } else if (password.isEmpty()) {
            etPassword.error = getString(R.string.error_pass)
            inputLayoutPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR)
            etPassword.requestFocus()
            return false
        } else if (password.length < 6) {
            etPassword.error = getString(R.string.error_pass_length)
            inputLayoutPassword.setPasswordVisibilityToggleTintMode(PorterDuff.Mode.CLEAR)
            etPassword.requestFocus()
            return false
        } else {
            return true
        }

    }
}