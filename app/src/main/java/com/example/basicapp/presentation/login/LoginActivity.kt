package com.example.basicapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.basicapp.R
import com.example.basicapp.databinding.ActivityMainBinding
import com.example.basicapp.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariable()
        checkIfUserAlreadyLogin()
        setOnClickListener()
        setObservers()
    }

    private fun checkIfUserAlreadyLogin() {
        if (viewModel.isUserAlreadySignedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun setObservers() {
        viewModel.authState.observe(this) { authState ->
            when (authState) {
                is AuthState.SUCCESS -> {
                    Log.d("MainActivity", "Authentication Successful")
                    binding.progressBarInclude.visibility = View.GONE
                    startActivity(Intent(this, HomeActivity::class.java))
                }

                is AuthState.CONNECTING -> {
                    binding.progressBarInclude.visibility = View.VISIBLE
                }

                is AuthState.ERROR -> {
                    Log.d("MainActivity", "Authentication Failed: ${authState.errorMessage}")
                    binding.progressBarInclude.visibility = View.GONE
                    Toast.makeText(this, "Login Failed: ${authState.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            signIn.setOnClickListener(this@LoginActivity)
        }
    }

    private fun initVariable() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.signIn -> onSignInButtonClick()
            else -> {
                Log.d("MainActivity", "Unknown view clicked")
            }
        }
    }

    private fun onSignInButtonClick() {
        val email = binding.username.text.toString()
        val password = binding.password.text.toString()
        viewModel.authenticateUser(email, password)
    }
}
