package com.elacqua.findmyrouteapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elacqua.findmyrouteapp.R
import com.elacqua.findmyrouteapp.ui.map.MapsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignInListener()
        loginStatusObserver()
        buttonSignUpListener()
        registerStatusObserver()
    }

    private fun buttonSignInListener() {
        btn_login_sign_in.setOnClickListener {
            val username = txt_login_username.text.toString()
            val password = txt_login_password.text.toString()
            val isInputValid = checkUserCredentials(username, password)
            if (isInputValid){
                viewModel.getUser(username, password)
            }
        }
    }

    private fun checkUserCredentials(username: String, password: String): Boolean {
        return when {
            username.isBlank() -> {
                val message = getString(R.string.login_invalid_username)
                makeShortToast(message)
                false
            }
            password.isBlank() -> {
                val message = getString(R.string.login_invalid_password)
                makeShortToast(message)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginStatusObserver() {
        viewModel.loginStatus.observe(this, { status ->
            if (status){
                navigateToMapActivity()
            } else {
                val message = getString(R.string.login_wrong_credentials)
                makeShortToast(message)
            }
        })
    }

    private fun buttonSignUpListener() {
        btn_login_sign_up.setOnClickListener {
            val username = txt_login_username.text.toString()
            val password = txt_login_password.text.toString()
            val isInputValid = checkUserCredentials(username, password)
            if (isInputValid) {
                viewModel.addUser(username, password)
            }
        }
    }

    private fun registerStatusObserver() {
        viewModel.registerStatus.observe(this, { status ->
            val message = if (status){
                getString(R.string.login_sign_up_success)
            } else {
                getString(R.string.login_username_exist)

            }
            makeShortToast(message)
        })
    }

    private fun navigateToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun makeShortToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}