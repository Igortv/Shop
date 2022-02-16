package com.example.shop.presentation.login

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shop.App
import com.example.shop.databinding.ActivityLoginBinding
import com.example.shop.presentation.arch.BaseAppCompatActivity
import com.example.shop.viewmodelfactories.LoginViewModelFactory
import javax.inject.Inject


class LoginActivity : BaseAppCompatActivity<LoginViewState, LoginViewModel>() {
    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var vmFactory: LoginViewModelFactory

    override fun createViewModel(): LoginViewModel {
        val vm = ViewModelProvider(this, vmFactory)
            .get(LoginViewModel::class.java)
        return vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComp().inject(this)

        initViewStateLiveData()

        binding.buttonOk.setOnClickListener {
            viewModel.login(binding.editTextLogin.text.toString(), binding.editTextPassword.text.toString())
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    override fun viewStateHandler(viewState: LoginViewState): () -> Unit = when(viewState) {
        LoginViewState.OK -> {
            {}
        }
        is LoginViewState.LoggedAsAdmin -> {
            {
                finish()
            }
        }
        is LoginViewState.WrongCredentials -> {
            {
                Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_SHORT).show()
            }
        }
        is LoginViewState.Error -> {
            {
                Toast.makeText(this, viewState.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}