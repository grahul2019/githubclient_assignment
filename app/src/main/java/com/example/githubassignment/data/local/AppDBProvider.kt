package com.example.githubassignment.data.local

import androidx.room.Room
import com.example.githubassignment.GitHubAssignmentApplication

object AppDBProvider{

    private const val DATABASE_NAME = "GitHubDB"

    val  crateDB: AppDatabase by lazy{
        Room.databaseBuilder(
            GitHubAssignmentApplication.getAppContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}