package com.example.shop.presentation.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shop.App
import com.example.shop.databinding.ActivityLoginBinding
import com.example.shop.viewmodelfactories.LoginViewModelFactory
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var vmFactory: LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComp().inject(this)

        viewModel = ViewModelProvider(this, vmFactory)
            .get(LoginViewModel::class.java)

        viewModel.viewState.observe(this, Observer { result ->
            handleStateChange(result)
        })

        binding.buttonOk.setOnClickListener {
            viewModel.login(binding.editTextLogin.text.toString(), binding.editTextPassword.text.toString())
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun handleStateChange(viewState: LoginViewState) {
        when(viewState) {
            is LoginViewState.LoggedAsAdmin -> {
                finish()
            }
            is LoginViewState.WrongCredentials -> {
                Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_SHORT).show()
            }
            is LoginViewState.Error -> {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}