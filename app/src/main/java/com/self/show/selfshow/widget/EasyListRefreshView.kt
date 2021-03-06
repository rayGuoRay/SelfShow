package com.self.show.selfshow.widget

import android.content.Context
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.self.show.selfshow.R

/**
 * Created by guolei on 18-1-30.
 */
class EasyListRefreshView constructor(context: Context, attrs: AttributeSet): SwipeRefreshLayout(context, attrs),
        SwipeRefreshLayout.OnRefreshListener {

    private var mContext: Context? = null
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLinearLayoutManager: LinearLayoutManager
    private lateinit var mEasyRefreshAdapter: EasyRefreshAdapter
    private var mIsLoadingMore: Boolean = false
    private var mSlideCallback: SlideCallback? = null
    private var mHandler: Handler? = null


    interface SlideCallback {
        fun onSlideToTopLoad()
        fun onSlideToBottomLoad(lastPosition: Int)
    }

    init {
        mContext = context
        initParam(context, attrs)
        initView()
    }

    fun setSlideCallback(slideCallback: SlideCallback) {
        mSlideCallback = slideCallback
    }

    fun bindAdapter(callback: EasyRefreshAdapter.EasyHolderCallback) {
        mEasyRefreshAdapter.setHolderCallback(callback)
    }

    fun setSourceList(list: List<*>) {
        mEasyRefreshAdapter.setSource(list)
        mEasyRefreshAdapter.notifyDataSetChanged()
    }

    fun setSwipeRefresh(isRefreshingParam: Boolean) {
        isRefreshing = isRefreshingParam
        mSwipeRefreshLayout.isRefreshing = isRefreshingParam
    }

    fun getSourceList(): List<*>? {
        return mEasyRefreshAdapter.getSource()
    }

    private fun initParam(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyRefreshListView)
        val normalLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshListView_normalLayout, -1)
        val loadingLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshListView_loadingLayout, -1)
        val noMoreLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshListView_noMoreLayout, -1)
        val errorLayoutId = typedArray.getResourceId(R.styleable.EasyRefreshListView_errorLayout, -1)
        typedArray.recycle()
        mEasyRefreshAdapter = EasyRefreshAdapter(context)
        mEasyRefreshAdapter.bindRelativeLayout(normalLayoutId, loadingLayoutId, noMoreLayoutId, errorLayoutId)

        mHandler = Handler()
    }

    private fun initView() {
        val easyListView = LayoutInflater.from(mContext).inflate(R.layout.layout_component_easy_list_refresh, this, true)
        mSwipeRefreshLayout = easyListView.findViewById(R.id.component_easy_list_swipe_layout)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mRecyclerView = easyListView.findViewById(R.id.component_easy_list_recycler_view)
        mLinearLayoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mEasyRefreshAdapter
        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollToBottomToLoadMore()
            }
        })
    }

    override fun onRefresh() {
        if (mSlideCallback != null) {
            mSlideCallback!!.onSlideToTopLoad()
        }
    }

    private fun scrollToBottomToLoadMore() {
        val lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition()
        if (lastVisiblePosition + 1 == mEasyRefreshAdapter.itemCount) {
            if (mSwipeRefreshLayout.isRefreshing) {
                mEasyRefreshAdapter.notifyItemRemoved(lastVisiblePosition + 1)
                return
            }
            if (!mIsLoadingMore) {
                mIsLoadingMore = true
                if (mSlideCallback != null) {
                    mSlideCallback!!.onSlideToBottomLoad(lastVisiblePosition)
                }
            }
        }
    }
}