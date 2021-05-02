package com.example.githubassignment.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubassignment.utils.USER

@Entity(tableName = USER)
data class User(
    @ColumnInfo(name = "username") @PrimaryKey val userName: String,
    @ColumnInfo(name = "password") val password: String,
)