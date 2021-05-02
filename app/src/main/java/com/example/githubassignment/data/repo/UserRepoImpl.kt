package com.example.githubassignment.data.repo

import com.example.githubassignment.data.local.UserDataSourceImpl
import com.example.githubassignment.data.local.models.User

class UserRepoImpl :UserRepo{

    private val mdataSource by lazy { UserDataSourceImpl() }

    override fun saveUser(username: String, password: String) {
        mdataSource.saveUser(username,password)
    }

    override fun getUser(username: String, password: String): User? = mdataSource.getUser(username,password)

    override fun getUsers(): List<User> = mdataSource.getUsers()

}