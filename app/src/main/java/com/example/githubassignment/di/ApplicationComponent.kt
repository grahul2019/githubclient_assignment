package com.example.githubassignment.di

import com.example.githubassignment.GitHubAssignmentApplication
import com.example.githubassignment.ui.home.activity.HomeActivity
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, DatabaseModule::class])
interface ApplicationComponent {
    fun inject(testApplication: GitHubAssignmentApplication?)
    fun inject(mainActivity: HomeActivity?)
}