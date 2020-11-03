package com.hend.cataloguechallenge.ui.base

import androidx.lifecycle.ViewModel
import com.hend.cataloguechallenge.utils.cancelIfActive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * An abstract class for ViewModels to avoid writing the same extensions multiple times.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    /** Coroutine's background job **/
    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    /** Clear our job when the linked activity is destroyed to avoid memory leaks **/
    override fun onCleared() {
        super.onCleared()
        job.cancelIfActive()
    }

}
