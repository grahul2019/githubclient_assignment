package com.example.githubassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.data.models.RemoteRepositoryData

@Dao
interface SearchRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepo(repo: DBRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepos(repos: List<DBRepository>)

    @Query("SELECT * FROM repository WHERE id = :id")
    fun getRepo(id: Int): DBRepository?

    @Query("SELECT * FROM repository WHERE name LIKE :searchKey ORDER BY watchers_count DESC")
    fun getRepos(searchKey: String): List<DBRepository>
}