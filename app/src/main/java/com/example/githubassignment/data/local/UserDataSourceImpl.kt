package com.example.githubassignment.data.local

import com.example.githubassignment.data.local.models.User

class UserDataSourceImpl :UserDataSource{

    private val dbProvider = AppDBProvider.crateDB.userDao()

    override fun saveUser(username: String, password: String) {
        dbProvider.saveUser(User(username,password))
    }

    override fun getUser(username: String, password: String): User? {
        return dbProvider.getUser(username,password)
    }

    override fun getUsers(): List<User> {
        return dbProvider.getUsers()
    }

}