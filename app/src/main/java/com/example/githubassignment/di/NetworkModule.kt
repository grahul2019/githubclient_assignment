package com.example.githubassignment.di

import android.content.Context
import com.example.githubassignment.BuildConfig
import com.example.githubassignment.data.GitHubClientService
import com.example.githubassignment.data.repo.GitHubRepoImpl
import com.example.githubassignment.data.repo.GithubRepo
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
abstract class NetworkModule {
    @Binds
    abstract fun bindGitRepo(gitHubRepoImpl: GitHubRepoImpl): GithubRepo

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideRemoteApiService(retrofit: Retrofit): GitHubClientService {
            return retrofit.create(GitHubClientService::class.java)
        }

        @Provides
        @JvmStatic
        fun provideBaseUrl(): String {
            return BuildConfig.APP_URL
        }

        @Provides
        @JvmStatic
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }


        @Provides
        @JvmStatic
        fun provideGson(): Gson = Gson()

        @Provides
        @JvmStatic
        fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)

        @Provides
        @JvmStatic
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()

        @Provides
        @JvmStatic
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            baseUrl: String
        ): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()
        }
    }
}