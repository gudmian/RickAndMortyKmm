package com.example.rickandmortykmm.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * Multiplatform ViewModel base. On Android the ViewModelStore drives lifecycle;
 * on iOS (UIKit) the holder must call [destroy] when the screen is dismissed
 * so viewModelScope is cancelled and coroutines do not leak.
 */
abstract class BaseViewModel : ViewModel() {

    protected val scope: CoroutineScope
        get() = viewModelScope

    /** Called from iOS when the owning view controller is deallocated. */
    fun destroy() {
        viewModelScope.cancel()
    }
}
