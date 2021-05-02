package com.example.githubassignment.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubassignment.utils.REPOSITORY

@Entity(tableName = REPOSITORY)
data class DBRepository(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner") val ownerImage: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "has_downloads") val hasDownloads: Boolean?,
    @ColumnInfo(name = "has_projects") val hasProjects: Boolean?,
    @ColumnInfo(name = "has_issues") val hasIssues: Boolean?,
    @ColumnInfo(name = "has_wiki") val hasWiki: Boolean?,
    @ColumnInfo(name = "watchers_count") val watchersCount: Int,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    @ColumnInfo(name = "commits_url") val commitsUrl: String,
    @ColumnInfo(name = "contributors_url") val contributorsUrl: String,
    @ColumnInfo(name = "favorite") var isFavorite: Boolean = false
)