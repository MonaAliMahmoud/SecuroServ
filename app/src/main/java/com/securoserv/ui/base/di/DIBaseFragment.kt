package com.securoserv.ui.base.di

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.securoserv.R
import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.data.utils.ErrorModel
import com.securoserv.di.helper.Injectable
import com.securoserv.ui.base.BaseFragment
import com.securoserv.ui.base.BaseRepository
import com.securoserv.ui.base.BaseViewModel
import com.securoserv.ui.base.ViewState
import javax.inject.Inject

abstract class DIBaseFragment<Binding : ViewDataBinding, Repository : BaseRepository, VM : BaseViewModel<Repository>> :
    BaseFragment<Binding>(), Injectable {

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var sharedPrefUtils: SharedPrefUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initView()

        viewModel.state.observe(viewLifecycleOwner, Observer {
            baseRender(it)
        })
    }

    private fun baseRender(state: ViewState) {
        when (state) {
            is ViewState.Loading -> {
                render(state)
            }
            is ViewState.HideLoading -> {
                render(state)
            }
            is ViewState.Network -> {
                networkToast()
            }
            is ViewState.Error -> {
                showError(state.error!!)
                if (state.error.code == 403) {
                    render(state)
                }
            }
            is ViewState.AuthenticationError -> {
                Toast.makeText(activity, R.string.notUser, Toast.LENGTH_LONG).show()
                logout()
            }
            else -> {
                render(state)
            }
        }
    }

    abstract fun bindViewModel()

    abstract fun initView()

    abstract fun render(state: ViewState)

    private fun showError(errorModel: ErrorModel?) {
        errorModel?.apiError?.let {
            Toast.makeText(context, errorModel.apiError, Toast.LENGTH_LONG).show()
        }

        errorModel?.localError?.let {
            Toast.makeText(context, errorModel.localError, Toast.LENGTH_LONG).show()
        }
    }

    private fun networkToast() {
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show()
    }

    private fun logout() {
        clearUserData()
    }

    private fun clearUserData() {
        sharedPrefUtils.logout()
    }
}