package abdo.omer.notes.base

import abdo.omer.notes.data.client.network.SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {

    //Coroutine's background job
    private val job = Job()

    //Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    /* used everywhere to show a loading dialog in the screen based om the current activity
    *
    * it should be used with long running operations that require the blockage of the user interaction
    *
    * Examples include -> Network operations , Database operations , File access operations ... etc
    * */
    val showLoading = SingleLiveEvent<Boolean>()
    val showError = SingleLiveEvent<Any>()
    val showInfo = SingleLiveEvent<Any>()
    val showSuccess = SingleLiveEvent<Any>()

    //start with a logged of user
    val userLogged = MutableLiveData<Boolean>()


    //represents the current number of items in the cart to show in the cart icon
    val itemsInCart = MutableLiveData(0)

    override fun onCleared() {
        super.onCleared()
        showLoading.postValue(false)
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
        if (coroutineContext.isActive)
            coroutineContext.cancel()
    }

}