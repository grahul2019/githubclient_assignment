package com.example.githubassignment.data.local

import com.example.githubassignment.data.local.models.User

interface UserDataSource {

    fun saveUser(username: String, password: String)

    fun getUser(username: String, password: String): User?

    fun getUsers(): List<User>
}