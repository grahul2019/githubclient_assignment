package com.example.githubassignment.data.models

import com.google.gson.annotations.SerializedName

data class RepositoryListRemoteResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repositoryData: List<RemoteRepositoryData>
)

data class RemoteRepositoryData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("owner") val owner: ContributorData?,
    @SerializedName("description") val description: String?,
    @SerializedName("has_downloads") val hasDownloads: Boolean?,
    @SerializedName("has_projects") val hasProjects: Boolean?,
    @SerializedName("has_issues") val hasIssues: Boolean?,
    @SerializedName("has_wiki") val hasWiki: Boolean?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("watchers_count") val watchersCount: Int?,
    @SerializedName("commits_url") val commitsUrl: String?,
    @SerializedName("contributors_url") val contributorsUrl: String?
)

data class ContributorData(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val loginName: String?,
    @SerializedName("avatar_url") val profilePicUrl: String?,
    @SerializedName("repos_url") val reposLink: String?
)