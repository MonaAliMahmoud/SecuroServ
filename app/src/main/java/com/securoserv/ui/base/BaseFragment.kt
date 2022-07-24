package com.securoserv.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.securoserv.di.helper.Injectable
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binder: T

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this is Injectable) {
            AndroidSupportInjection.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(
            inflater, getLayoutId(), container, false
        )
        return binder.root
    }
}