package com.example.githubassignment.data.local

import com.example.githubassignment.data.models.DBRepository

class GithubRepoDataSourceImpl : GithubRepoDataSource{

    private val dbProvider = AppDBProvider.crateDB.searchRepo()

    override fun saveRepositories(repositories: List<DBRepository>) {
        dbProvider.saveRepos(repositories)
    }

    override fun favoriteRepo(repo: DBRepository) {
        dbProvider.saveRepo(repo)
    }

    override fun searchRepositories(searchKey: String): List<DBRepository> {
       return dbProvider.getRepos(searchKey)
    }

    override fun getDBRepo(id: Int): DBRepository? = dbProvider.getRepo(id)

}