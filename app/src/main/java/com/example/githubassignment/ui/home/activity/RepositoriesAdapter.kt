package com.example.githubassignment.ui.home.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubassignment.R
import com.example.githubassignment.data.models.DBRepository
import com.example.githubassignment.databinding.ItemRepositoryBinding

class RepositoriesAdapter(
    private val onClick: (data: DBRepository) -> Unit,
    private val onFavClick: (data: DBRepository) -> Unit
) : ListAdapter<DBRepository, RepositoriesAdapter.RepoViewHolder>(DIFF) {

    override fun onCreateViewHolder(viewHolder: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(viewHolder.context),
                viewHolder,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    inner class RepoViewHolder(private val item: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(repo: DBRepository) {
            item.apply {
                tvNameContent.text = repo.name
                tvFullNameContent.text = repo.fullName
                tvWatcherCountContent.text = repo.watchersCount.toString()
                Glide.with(item.root.context)
                    .load(repo.ownerImage)
                    .apply {
                        placeholder(R.drawable.ic_image_placeholder)
                        error(R.drawable.ic_image_error_placeholder)
                    }
                    .into(ivProfileImage)
                clRoot.setOnClickListener { onClick(repo.apply {
                    isFavorite = !isFavorite
                }) }

                if (repo.isFavorite) {
                    ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            item.root.context,
                            R.drawable.ic_favorite
                        )
                    )
                } else {
                    ivFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            item.root.context,
                            R.drawable.ic_unfavorite
                        )
                    )
                }

                ivFavorite.setOnClickListener {
                    onFavClick(repo.apply {
                        isFavorite = !isFavorite
                    })
                }
            }
        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<DBRepository>() {

            override fun areItemsTheSame(old: DBRepository, new: DBRepository) = old.id == new.id

            override fun areContentsTheSame(old: DBRepository, new: DBRepository) = old == new
        }
    }
}