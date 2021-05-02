package com.example.githubassignment.data.repo

import com.example.githubassignment.data.local.models.User

interface UserRepo {

    fun saveUser(username: String, password: String)

    fun getUser(username: String, password: String) : User?

    fun getUsers(): List<User>
}