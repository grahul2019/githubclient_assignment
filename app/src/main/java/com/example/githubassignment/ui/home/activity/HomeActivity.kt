package com.example.githubassignment.ui.home.activity

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.databinding.ActivityMainBinding
import com.example.githubassignment.ui.base.activities.BaseActivity
import com.example.githubassignment.ui.home.viewmodels.HomeViewModel
import com.example.githubassignment.utils.toast
import java.lang.ref.WeakReference

class HomeActivity : BaseActivity<HomeViewModel,ActivityMainBinding>(HomeViewModel::class.java) {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private val repositoriesAdapter by lazy {
        RepositoriesAdapter(::toggleFavorite, ::toggleFavorite)
    }

    override fun setupViews() {

        fun setupRepoList() {
            getBinding()?.rvRepos?.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity,RecyclerView.VERTICAL,false)
                adapter = repositoriesAdapter
                if (itemDecorationCount < 1) {
                    addItemDecoration(
                        DividerItemDecoration(
                            this@HomeActivity,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )
                }
            }
        }

        fun setupSearch() {
            getBinding()?.etSearchRepo?.requestFocus()
            getBinding()?.etSearchRepo?.setOnEditorActionListener { v, actionId, event ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchText = getBinding()?.etSearchRepo?.text.toString().trim()
                        .takeIf { it.isNotBlank() } ?: run {
                        toast("Search for repository")
                        return@setOnEditorActionListener false
                    }

                    viewModel.searchResults(searchText)

                    return@setOnEditorActionListener true
                }

                false
            }
        }

        setupRepoList()
        setupSearch()
    }

    override fun observeViewModel() {
        viewModel.apply {
            repos.observe(this@HomeActivity) { repoList ->
                getBinding()?.tvNoResults?.isVisible = repoList.isNullOrEmpty()

                repositoriesAdapter.submitList(repoList)
                repositoriesAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun toggleFavorite(data: DBRepository) {
        viewModel.toggleFavorite(data)
    }

    companion object{
        fun newInstance(weakReference: WeakReference<Context>)= Intent(
            weakReference.get(),
            HomeActivity::class.java
        )
    }

}