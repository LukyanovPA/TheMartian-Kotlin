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
            is ResourceState.Success -> handleSuccessStateMovies(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateMovies(true)
            is ResourceState.Error -> handleErrorStateMovies(resourceState.error)
        }
    }

    open fun handleSuccessStateMovies(data: T) {
        handleLoadingStateMovies(false)
    }

    open fun handleLoadingStateMovies(state: Boolean) {

    }

    open fun handleErrorStateMovies(error: Throwable?) {
        handleLoadingStateMovies(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }
}