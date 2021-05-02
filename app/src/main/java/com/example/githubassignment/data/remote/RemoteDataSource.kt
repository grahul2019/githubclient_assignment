package com.example.githubassignment.data.remote

import com.example.githubassignment.BuildConfig
import com.example.githubassignment.data.GitHubClientService
import com.example.githubassignment.data.repo.GithubRepo
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RemoteDataSource : GithubRepo {

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(BuildConfig.APP_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideOkHttpClient(provideLoggingInterceptor()))
        .build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    private fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()



    private val remoteService: GitHubClientService = retrofitInstance.create(GitHubClientService::class.java)

    override fun getRepositories(searchKey: String, mCallback: GithubRepo.RemoteResponseCallBack) {
        val disposable = CompositeDisposable()
        disposable.add(
            remoteService.getRepositories(searchKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it?.repositoryData != null) {
                        it.repositoryData.let { repos -> mCallback.onSuccess(repos) }
                    } else {
                        mCallback.onFailure()
                    }
                    disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
                },{
                    mCallback.onFailure()
                    disposable.takeIf { disp -> !disp.isDisposed }?.dispose()
                })
        )
    }
}