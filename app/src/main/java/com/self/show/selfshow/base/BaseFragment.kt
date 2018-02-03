package com.self.show.selfshow.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by guolei on 18-1-22.
 */
abstract class BaseFragment: Fragment() {
    protected var mIsPrepared: Boolean = false
    protected var mIsVisible: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            mIsVisible = true
            onVisible()
        } else {
            mIsVisible = false
            onInvisible()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsPrepared = true
        lazyLoad()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsPrepared = false
    }

    protected fun onVisible() {
        lazyLoad()
    }

    protected fun onInvisible() {}

    protected fun lazyLoad() {
        if (!mIsVisible || !mIsPrepared) {
            return
        }
        loadData()
    }

    protected open fun loadData() {}
}