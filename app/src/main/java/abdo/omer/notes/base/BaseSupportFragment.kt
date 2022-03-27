package abdo.omer.notes.base

import abdo.omer.notes.R
import abdo.omer.notes.ui.progress.ProgressDialog
import abdo.omer.notes.utlis.CookieBarConfig
import abdo.omer.notes.utlis.getCustomColor
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

abstract class BaseSupportFragment: Fragment() {

    // Show and hide toolbar from fragment
    protected open var toolbarVisibility: Boolean = false
    // Show and hide Bottom bar and FAB button from fragment
    protected open var bottomBarVisibility: Boolean = false
    // Show and hide with fade animation or not from fragment
    protected open var withFadeAnimation: Boolean = true
    //Set toolbar title
    protected open var toolbarTitle: Int = 0

    //base viewModel for configuring info,success, failure , loading ,user login events
    abstract val viewModel: BaseViewModel

    //full screen loading dialog
    val progressDialog by lazy { ProgressDialog(activity) }

    //basic navigation controller for fragments within the app
    val navController by lazy { findNavController() }

    //Fragments who require the user to be logged in should override this with value true
    open var requireLogin = false

    //alert module , controlled through the base viewModel
    private val cookieBarConfig: CookieBarConfig by lazy { CookieBarConfig(requireActivity()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //viewModel.userLogged.value = !Constants().getAccessToken(requireContext()).isNullOrBlank()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //checkNetworkConnectivity(requireContext())

        viewModel.showLoading.observe(viewLifecycleOwner) { showLoading ->
            if (activity != null && !requireActivity().isFinishing) {
                if (showLoading)
                    view.postDelayed({ progressDialog.show() }, 100)
                else
                    view.postDelayed({ progressDialog.dismiss() }, 100)
            }
        }

        viewModel.showInfo.observe(viewLifecycleOwner) { infoMessage ->
            if (infoMessage is String)
                cookieBarConfig.showDefaultInfoCookie(infoMessage)
            else
                context?.resources?.getString(infoMessage as Int)?.let {
                    cookieBarConfig.showDefaultInfoCookie(it)
                }
        }


        viewModel.showSuccess.observe(viewLifecycleOwner) { infoMessage ->
            if (infoMessage is String)
                cookieBarConfig.showDefaultSuccessCookie(infoMessage)
            else
                context?.resources?.getString(infoMessage as Int)?.let {
                    cookieBarConfig.showDefaultSuccessCookie(it)
                }
        }

        viewModel.showError.observe(viewLifecycleOwner) { showError ->
            cookieBarConfig.runCatching {
                viewModel.showLoading.postValue(false)
                if (showError is String) {
                    view.post { this.showDefaultErrorCookie(error = showError) }
                    if (showError.contains("No address associated with hostname")) {
                        val alert =
                            Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                                .setBackgroundTint(context?.getCustomColor(R.color.appColor3)!!)
                                .setTextColor(context?.getCustomColor(R.color.status_bar_color)!!)
                        alert.setAction(R.string.retry) {
                            alert.dismiss()
                            refreshUI()
                        }.setActionTextColor(context?.getCustomColor(R.color.status_bar_color)!!)
                        alert.show()
                    } else {
                    }
                } else
                    context?.resources?.getString(showError as Int)?.let {
                        cookieBarConfig.showDefaultErrorCookie(it)
                    }
            }

        }

        onLazyInitView(savedInstanceState)

        setOnclickLister()
        viewModelObserver()
    }

    open fun onLazyInitView(savedInstanceState: Bundle?) {
    }

    open fun refreshUI() {

    }


    @SuppressLint("NewApi")
    private fun checkNetworkConnectivity(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        val isConnected: Boolean = networkCapabilities != null && networkCapabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET)

        if (!isConnected) viewModel.showError.postValue(R.string.internet_not_available)
    }

    fun toggleKeyboard(activity: Context, show: Boolean? = false) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            if (show != null) {
                if (!show) {
                    imm.hideSoftInputFromWindow(requireView().windowToken, 0) // hide
                } else {
                    imm.showSoftInput(view, 0) // show
                }
            } else {
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(requireView().windowToken, 0) // hide
                } else {
                    imm.showSoftInput(view, 0) // show
                }
            }
        }

    }

    fun showKeyboard(etText: EditText) {
        etText.requestFocus()
        toggleKeyboard(requireActivity(), true)
    }

    open fun setOnclickLister(){

    }

    open fun viewModelObserver(){

    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
    }

    fun appBarAndBottomBarVisibility(toolbar: Boolean, bottomBar: Boolean, withFade: Boolean, toolbarHeader: Int = 0){
        toolbarVisibility = toolbar
        bottomBarVisibility = bottomBar
        withFadeAnimation = withFade
        toolbarTitle = toolbarHeader
    }

}