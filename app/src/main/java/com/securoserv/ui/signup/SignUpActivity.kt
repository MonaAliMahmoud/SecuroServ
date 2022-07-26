package com.securoserv.ui.signup

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.securoserv.ui.MainActivity
import com.securoserv.R
import com.securoserv.ui.login.LoginActivity
import com.securoserv.utils.hideSoftKeyboard

class SignUpActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var inputLayoutPassword: TextInputLayout
    lateinit var btnSignUp: MaterialButton
    lateinit var loginLayout: LinearLayout

    var name: String = ""
    var email: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etName = this.findViewById(R.id.et_name)
        etEmail = this.findViewById(R.id.et_email)
        etPassword = this.findViewById(R.id.et_password)
        inputLayoutPassword = this.findViewById(R.id.input_layout_password)
        btnSignUp = this.findViewById(R.id.btn_signup)
        loginLayout = this.findViewById(R.id.layout_login)

        actions()
    }

    private fun actions() {

        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etName.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(50))
                name = p0.toString()
            }
        })

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
                        if (checkValidation(etName.text.toString().trim(),
                                etEmail.text.toString().trim(),
                                etPassword.text.toString().trim()
                            )
                        ) {
                            name = etName.text.toString().trim()
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

        btnSignUp.setOnClickListener {
            if (checkValidation(name, email, password)) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        loginLayout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkValidation(name: String, email: String, password: String): Boolean {
        if (name.isEmpty()) {
            etName.error = getString(R.string.error_name)
            etName.requestFocus()
            return false
        }else if (email.isEmpty()) {
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