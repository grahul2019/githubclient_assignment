package com.example.githubassignment.di

import android.app.Application
import android.content.Context
import dagger.Module

import dagger.Provides

import javax.inject.Singleton


@Module
class ApplicationModule(private val mApplication: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return mApplication
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return mApplication
    }
}