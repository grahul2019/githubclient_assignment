package com.example.githubassignment.ui.login

import android.content.Context
import android.content.Intent
import com.example.githubassignment.databinding.ActivityLoginBinding
import com.example.githubassignment.ui.base.activities.BaseActivity
import com.example.githubassignment.ui.home.activity.HomeActivity
import com.example.githubassignment.utils.toast
import java.lang.ref.WeakReference

class LoginActivity : BaseActivity<LoginViewModel,ActivityLoginBinding>(
    LoginViewModel::class.java
){
    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initListeners() {
        getBinding()?.apply {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    toast("Enter valid name")
                    return@setOnClickListener
                }

                val password = etPassword.text.toString().trim().takeIf { it.isNotBlank() } ?: run {
                    toast("Password error")
                    return@setOnClickListener
                }

                viewModel.loginUser(username, password)
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.apply {
            userType.observe(this@LoginActivity) { userType ->
                when (userType) {
                    UserType.NEW -> {
                        toast("Welcome")
                        navigateToHomeScreen()
                    }
                    UserType.EXISTING -> {
                        toast("Welcome back !!")
                        navigateToHomeScreen()
                    }
                    else -> {
                        toast("Erorr logging in")
                    }
                }
            }
        }
    }

    private fun navigateToHomeScreen() {
        startActivity(HomeActivity.newInstance(WeakReference(this)))
        finish()
    }

    companion object {

        fun newInstance(context: WeakReference<Context>) = Intent(
            context.get(),
            LoginActivity::class.java
        )
    }

}