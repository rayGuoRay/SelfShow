package com.self.show.selfshow.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.self.show.selfshow.R
import com.self.show.selfshow.base.BaseFragment
import android.os.Handler
import android.widget.ListView


/**
 * Created by guolei on 18-1-22.
 */
class SelfCollectFragment: BaseFragment() {

    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var mListView: ListView
    private val data = ArrayList<String>()
    private var mHandler: Handler = Handler();

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_self_collect, container, false)
        mSwipeRefresh = rootView.findViewById(R.id.swipe_layout)
        return rootView
    }
}