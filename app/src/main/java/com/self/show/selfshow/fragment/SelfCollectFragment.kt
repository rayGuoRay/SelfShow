package com.self.show.selfshow.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.self.show.selfshow.R
import com.self.show.selfshow.base.BaseFragment

/**
 * Created by guolei on 18-1-22.
 */
class SelfCollectFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_self_collect, container, false)
        return rootView
    }
}