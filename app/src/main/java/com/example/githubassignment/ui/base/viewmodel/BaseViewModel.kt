package com.example.githubassignment.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel(){
    /**
    SupervisorJob that handles each task as a separate child.
     */
    private val job = SupervisorJob()

    /**
     * CoroutineScope that dispatches the task on the Dispatchers.Main thread by default.
     */
    val uiScope = CoroutineScope(job + Dispatchers.Main)

    /**
     * CoroutineScope that dispatches the task on the Dispatchers.IO thread by default.
     */
    val ioScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCleared() {
        super.onCleared()
        // Cancelling all the children of the SupervisorJob at once if the
        // ViewModel gets cleared.
        job.cancel()
    }
}