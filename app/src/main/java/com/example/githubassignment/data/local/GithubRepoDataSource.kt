package com.example.githubassignment.data.local

import com.example.githubassignment.data.models.DBRepository

interface GithubRepoDataSource {

    fun saveRepositories(repositories: List<DBRepository>)

    fun favoriteRepo(repo: DBRepository)

    fun searchRepositories(searchKey: String): List<DBRepository>

    fun getDBRepo(id: Int) : DBRepository?
}