package com.example.githubassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubassignment.data.local.dao.SearchRepoDao
import com.example.githubassignment.data.local.dao.UserDao
import com.example.githubassignment.data.local.models.User
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.data.models.RemoteRepositoryData

@Database(entities = [DBRepository::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao():UserDao

    abstract fun searchRepo(): SearchRepoDao
}