package com.example.githubassignment.ui.splash.activity

import com.example.githubassignment.databinding.ActivitySplashBinding
import com.example.githubassignment.ui.base.activities.BaseActivity
import com.example.githubassignment.ui.home.activity.HomeActivity
import com.example.githubassignment.ui.login.LoginActivity
import com.example.githubassignment.ui.splash.viewmodels.Destination
import com.example.githubassignment.ui.splash.viewmodels.SplashViewModel
import com.example.githubassignment.utils.toast
import java.lang.ref.WeakReference

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(
    SplashViewModel::class.java
) {

    override fun setupViews() {
        super.setupViews()
        viewModel.checkAndNavigate()
    }

    override fun observeViewModel() {
        viewModel.apply {
            mDestination.observe(this@SplashActivity) { destination ->
                when (destination) {
                    Destination.LOGIN -> navigateToAccessScreen()
                    Destination.HOME -> navigateToHomeScreen()
                    else -> toast("Error navigating")
                }
            }
        }
    }

    private fun navigateToAccessScreen() {
        startActivity(LoginActivity.newInstance(WeakReference(this)))
        finish()
    }

    private fun navigateToHomeScreen() {
        startActivity(HomeActivity.newInstance(WeakReference(this)))
        finish()
    }

    override fun getViewBinding(): ActivitySplashBinding  = ActivitySplashBinding.inflate(layoutInflater)

}