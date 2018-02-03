package com.self.show.selfshow.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.self.show.selfshow.R
import com.self.show.selfshow.base.BaseFragment

/**
 * Created by guolei on 18-1-22.
 */
class UsRankFragment: BaseFragment() {

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null;
    private var mRecyclerView: RecyclerView? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_usrank, container, false)
//        mSwipeRefreshLayout = rootView.findViewById(R.id.fragment_us_rank_swipe_refresh)
//        mRecyclerView = rootView.findViewById(R.id.fragment_us_rank_recycler_view)
        return rootView
    }

    override fun loadData() {
        super.loadData()
    }
}