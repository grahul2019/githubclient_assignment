package com.example.githubassignment.data

import com.example.githubassignment.data.models.RepositoryListRemoteResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubClientService {
    @GET("search/repositories")
    fun getRepositories(@Query("q") query: String): Observable<RepositoryListRemoteResponse>
}