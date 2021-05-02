package com.example.githubassignment.data.repo

import com.example.githubassignment.data.models.RemoteRepositoryData

interface GithubRepo{
    fun getRepositories(searchKey: String,mCallback:RemoteResponseCallBack)

    interface RemoteResponseCallBack {
        fun onSuccess(repos: List<RemoteRepositoryData>)
        fun onFailure()
    }
}

