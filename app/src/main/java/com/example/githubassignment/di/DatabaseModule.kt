package com.example.githubassignment.di

import android.content.Context
import androidx.room.Room
import com.example.githubassignment.data.local.AppDatabase
import com.example.githubassignment.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule( context: Context) {

    private val mContext: Context = context

    private val mDBName = "githubapp_database.db"
    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            mContext,
            AppDatabase::class.java,
            mDBName
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDatabaseName(): String {
        return mDBName
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

}