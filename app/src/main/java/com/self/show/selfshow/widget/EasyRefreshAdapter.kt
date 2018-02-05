package com.self.show.selfshow.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.self.show.selfshow.utils.LogUtils

/**
 * Created by guolei on 18-1-30.
 */
class EasyRefreshAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val TYPE_ITEM_NORMAL: Int = 0
        private val TYPE_ITEM_LOADING: Int = 1
        private val TYPE_ITEM_NOMORE: Int = 2
        private val TYPE_ITEM_ERROR: Int = 3
    }

    private var mContext: Context? = null
    private var mNormalLayoutId: Int = -1
    private var mLoadingLayoutId: Int = -1
    private var mNoMoreLayoutId: Int = -1
    private var mErrorLayoutId: Int = -1
    private var mEasyHolderCallback: EasyHolderCallback? = null
    private var mEasySourceList: List<*>? = null

    interface EasyHolderCallback {
        fun onCreateNormal(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder
        fun onBindNormal(holder: RecyclerView.ViewHolder, position: Int)
    }

    init {
        mContext = context
    }

    fun setSource(list: List<*>) {
        mEasySourceList = list
    }

    fun getSource(): List<*>? {
        return mEasySourceList
    }

    fun setHolderCallback(callback: EasyHolderCallback) {
        mEasyHolderCallback = callback
    }

    override fun getItemViewType(position: Int): Int {
//        if (hasFootView() && position + 1 == itemCount) {
//            when (mFootState) {
//                FOOT_STATE_LOADING -> return TYPE_ITEM_LOADING
//                FOOT_STATE_LOAD_NOMORE -> return TYPE_ITEM_NOMORE
//                FOOT_STATE_LOAD_ERROR -> return TYPE_ITEM_ERROR
//            }
//        }
        return TYPE_ITEM_NORMAL
    }

    override fun getItemCount(): Int = if (mEasySourceList == null) 0 else mEasySourceList!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return mEasyHolderCallback!!.onCreateNormal(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        return mEasyHolderCallback!!.onBindNormal(holder, position)
    }

    @JvmOverloads fun bindRelativeLayout(normalId: Int, loadingId: Int = -1, noMoreId: Int = -1, errorId:Int = -1) {
        mNormalLayoutId = normalId
        mLoadingLayoutId = loadingId
        mNoMoreLayoutId = noMoreId
        mErrorLayoutId = errorId
    }

    fun hasFootView() = mLoadingLayoutId > 0 || mNoMoreLayoutId > 0 && mErrorLayoutId > 0
}