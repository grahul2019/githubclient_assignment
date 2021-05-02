package com.example.githubassignment

import android.content.Context
import androidx.multidex.MultiDexApplication
import java.lang.ref.WeakReference

class GitHubAssignmentApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appContext = WeakReference(applicationContext)
    }

    companion object {
        private var appContext: WeakReference<Context>? = null

        fun getAppContext() = appContext?.get() ?: throw RuntimeException("Context not found")
    }

}