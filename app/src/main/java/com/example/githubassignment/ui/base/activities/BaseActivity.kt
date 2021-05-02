package com.example.githubassignment.ui.base.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.example.githubassignment.R
import com.example.githubassignment.ui.base.viewmodel.BaseViewModel
import com.example.githubassignment.utils.snack
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VM : BaseViewModel,VB:ViewBinding>(viewModelClass: Class<VM>) : FragmentActivity() {

    private var mSnackBar:Snackbar?=null

    // Broadcast Receiver to check for the internet connectivity.
    private val mNetworkState = InternetConnectionStateReciever()
    private val mNetworkFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    // Boolean to inform whether the internet connectivity is available at the moment.
    private var isNetworkAvailable: Boolean = true


    protected val viewModel: VM by lazy { ViewModelProviders.of(this)[viewModelClass] }

    protected var mbinding:VB?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = getViewBinding()
        setContentView(mbinding?.root)
        setupViews()
        initListeners()
        observeViewModel()
    }

    abstract fun getViewBinding(): VB

    fun getBinding() = mbinding

    /**
     * Display "No network connection" message/UI based on the current state
     * of the network connectivity.
     */
    open fun showInternetError(show:Boolean){}
    /**
     * This method should be used to setup views. e.g Setting RecyclerView Adapters or
     * showing initial state of Activities
     */
    open fun setupViews() {}

    /**
     * This method should be used to initialise the listeners for a view or multiple
     * views in the activity
     */
    open fun initListeners() {}

    /**
     * The viewmodel's LiveData should be observed by overriding this method in the
     * inheriting classes
     */
    open fun observeViewModel() {}


    /**
     * Function to notify the network connectivity state.
     */
    open fun getNetworkAvailability(isConnected: Boolean) {}


    override fun onResume() {
        super.onResume()
        registerReceiver(mNetworkState, mNetworkFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mNetworkState)
    }

    /**
     * Returns the current state of the network connectivity.
     */
    fun getInternetConnectionState() = isNetworkAvailable

    /**
     * BroadcastReceiver Class to listen to the network state changes.
     */
    inner class InternetConnectionStateReciever : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {
                val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnected == true

                getNetworkAvailability(isConnected)
                isNetworkAvailable = isConnected

                if (!isConnected) {
                    showInternetError(true)
                    mSnackBar = getBinding()?.root?.snack(getString(R.string.no_interet_connection),Snackbar.LENGTH_INDEFINITE)
                } else {
                    showInternetError(false)
                    mSnackBar?.dismiss()
                }
                cm
            }
        }
    }
}

