package com.example.githubassignment.data.repo


import android.util.Log
import com.example.githubassignment.data.local.GithubRepoDataSourceImpl
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.data.models.RemoteRepositoryData
import com.example.githubassignment.data.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GitHubAppRepo {

    private val database = GithubRepoDataSourceImpl()
    private val remote = RemoteDataSource()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    suspend fun fetchRepositories(searchKey: String): List<DBRepository>? = suspendCoroutine {
        remote.getRepositories(searchKey, object : GithubRepo.RemoteResponseCallBack {

            override fun onSuccess(repos: List<RemoteRepositoryData>) {
                ioScope.launch {
                    val mapped = repos.map { repo ->
                        DBRepository(
                            id = repo.id,
                            name = repo.name ?: "",
                            ownerImage = repo.owner?.profilePicUrl ?: "",
                            description = repo.description ?: "",
                            fullName = repo.fullName ?: "",
                            watchersCount = repo.watchersCount ?: 0,
                            htmlUrl = repo.htmlUrl ?: "",
                            hasDownloads = repo.hasDownloads ?: false,
                            hasProjects = repo.hasProjects ?: false,
                            hasIssues = repo.hasIssues ?: false,
                            hasWiki = repo.hasWiki ?: false,
                            commitsUrl = repo.commitsUrl ?: "",
                            contributorsUrl = repo.contributorsUrl ?: "",
                            isFavorite = database.getDBRepo(repo.id)?.isFavorite ?: false
                        )
                    }

                    database.saveRepositories(mapped)

                    it.resume(database.searchRepositories(searchKey))
                }
            }

            override fun onFailure() {
                Log.e(TAG, "Failed to fetch for the data.")
                it.resume(null)
            }
        })
    }

    fun getRepositories(searchKey: String): List<DBRepository> =
        database.searchRepositories(searchKey)

    fun favRepo(repo: DBRepository) = database.favoriteRepo(repo)

    companion object {
        val TAG: String = GithubRepo::class.java.simpleName
    }
}