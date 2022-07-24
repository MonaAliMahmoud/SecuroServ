package com.securoserv.ui.base.di

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.securoserv.R
import com.securoserv.data.local.SharedPrefUtils
import com.securoserv.data.utils.ErrorModel
import com.securoserv.ui.base.BaseRepository
import com.securoserv.ui.base.BaseViewModel
import com.securoserv.ui.base.ViewState
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class DIBaseActivityWithoutFragment<Binding : ViewDataBinding, Repository : BaseRepository, VM : BaseViewModel<Repository>> :
    AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModel: VM

    lateinit var binder: Binding

    @Inject
    lateinit var sharedPrefUtils: SharedPrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binder = DataBindingUtil.setContentView(this, getLayout())
        binder.lifecycleOwner = this
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        initView()

        viewModel.state.observe(this,
            Observer {
                baseRender(it)
            })
    }

    private fun baseRender(state: ViewState) {
        when (state) {
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
                Toast.makeText(this, R.string.notUser, Toast.LENGTH_LONG).show()
                logout()
            }
            else -> {
                render(state)
            }
        }
    }

    private fun logout() {
        clearUserData()
    }

    private fun clearUserData() {
        sharedPrefUtils.logout()
    }

    abstract fun render(state: ViewState)

    abstract fun initView()

    abstract fun getLayout(): Int

    private fun showError(message: ErrorModel) {
        message.apiError?.let {
            Toast.makeText(this, message.apiError, Toast.LENGTH_LONG).show()
        }

        message.localError?.let {
            Toast.makeText(this, message.localError, Toast.LENGTH_LONG).show()
        }
    }

    private fun networkToast() {
        Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show()
    }

    override fun androidInjector() = androidInjector
}