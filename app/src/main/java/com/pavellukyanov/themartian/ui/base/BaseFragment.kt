package com.pavellukyanov.themartian.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.domain.ResourceState

abstract class BaseFragment<T : Any>(
    resourceId: Int
) : Fragment(resourceId) {

    open fun onStateReceive(resourceState: ResourceState<T>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessState(resourceState.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(resourceState.error)
        }
    }

    open fun handleSuccessState(data: T) {
        handleLoadingState(false)
    }

    open fun handleLoadingState(state: Boolean) {

    }

    open fun handleErrorState(error: Throwable?) {
        handleLoadingState(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }
}