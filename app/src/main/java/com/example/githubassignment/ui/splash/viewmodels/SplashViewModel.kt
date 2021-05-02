package com.example.githubassignment.ui.splash.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubassignment.data.repo.UserRepoImpl
import com.example.githubassignment.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel : BaseViewModel() {

    private val userRepoImpl by lazy { UserRepoImpl() }

    private val _destination by lazy { MutableLiveData<Destination>() }
    val mDestination: LiveData<Destination> by lazy { _destination }

    fun checkAndNavigate() {
        ioScope.launch {
            delay(1500L)
            _destination.postValue(
                if (userRepoImpl.getUsers().isEmpty()) {
                    Destination.LOGIN
                } else {
                    Destination.HOME
                }
            )
        }
    }
}

enum class Destination {
    HOME,
    LOGIN
}