package com.example.githubassignment.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.data.repo.GitHubAppRepo
import com.example.githubassignment.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    private val githubRepo by lazy { GitHubAppRepo() }

    private val _searchTerm by lazy { MutableLiveData<String>() }

    private val _repos by lazy { MutableLiveData<List<DBRepository>>() }
    val repos: LiveData<List<DBRepository>> by lazy { _repos }

    fun searchResults(searchTerm: String) {
        _searchTerm.postValue(searchTerm)
        ioScope.launch {
            withContext(ioScope.coroutineContext) {
                githubRepo.fetchRepositories(
                    searchTerm
                )
            }?.let { repos ->
                _repos.postValue(repos)
                return@let
            } ?: run {
                _repos.postValue(githubRepo.getRepositories(searchTerm))
                return@run
            }
        }
    }

    fun toggleFavorite(data: DBRepository) {
        ioScope.launch {
            githubRepo.favRepo(data)
            _repos.postValue(githubRepo.getRepositories(_searchTerm.value ?: ""))
        }
    }
}