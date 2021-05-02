package com.example.githubassignment.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubassignment.data.repo.UserRepoImpl
import com.example.githubassignment.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val userRepoImpl by lazy { UserRepoImpl() }

    private val _userType by lazy { MutableLiveData<UserType>() }
    val userType: LiveData<UserType> by lazy { _userType }

    fun loginUser(username: String, password: String) {
        ioScope.launch {
            userRepoImpl.getUser(username, password)?.let {
                _userType.postValue(UserType.EXISTING)
                return@let
            } ?: run {
                userRepoImpl.saveUser(username, password)
                _userType.postValue(UserType.NEW)
                return@run
            }
        }
    }
}

enum class UserType {
    NEW, EXISTING
}