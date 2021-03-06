package com.self.show.selfshow.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.self.show.selfshow.MovieDetailActivity
import com.self.show.selfshow.R
import com.self.show.selfshow.base.BaseFragment
import com.self.show.selfshow.bean.UsBoxMovie
import com.self.show.selfshow.bean.UsBoxMovieList
import com.self.show.selfshow.network.RetrofitWrapper
import com.self.show.selfshow.widget.EasyListRefreshView
import com.self.show.selfshow.widget.EasyRefreshAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by guolei on 18-1-22.
 */
class UsRankFragment: BaseFragment() {

    private lateinit var mUsEasyRefresh: EasyListRefreshView
    private lateinit var mUsRankList: List<UsBoxMovie>

    private var mHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_usrank, container, false)
        mUsEasyRefresh = rootView.findViewById(R.id.us_rank_easy_refresh)
        return rootView
    }

    override fun loadData() {
        super.loadData()
        mUsEasyRefresh.bindAdapter(object : EasyRefreshAdapter.EasyHolderCallback {
            override fun onCreateNormal(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
                return MovieRankNormalViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_normal, parent, false))
            }

            override fun onBindNormal(holder: RecyclerView.ViewHolder, position: Int) {
                if (position == 0) {
                    (holder.itemView.layoutParams as RecyclerView.LayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.card_padding_vertical_size)
                } else {
                    (holder.itemView.layoutParams as RecyclerView.LayoutParams).topMargin = 0
                }
                holder.itemView.setOnClickListener({skipToMovieDetailPage(mUsRankList[position].subject.id)})
                (holder as UsRankFragment.MovieRankNormalViewHolder).movieTitle.text = mUsRankList[position].subject.title
                (holder as UsRankFragment.MovieRankNormalViewHolder).moviePic.setImageURI(mUsRankList[position].subject.images.large)
            }
        })
        mUsEasyRefresh.setSlideCallback(object : EasyListRefreshView.SlideCallback {
            override fun onSlideToTopLoad() {
                requestData()
            }

            override fun onSlideToBottomLoad(lastPosition: Int) {
                // do nothing
            }
        })
        mUsEasyRefresh.setSwipeRefresh(true)
        requestData()
    }

    private fun requestData() {
        val usRankBox = RetrofitWrapper.instance.mNetWorkApi.getUsBox()
        usRankBox.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { usBoxMovieList: UsBoxMovieList? ->
                    mUsRankList = usBoxMovieList!!.subjects
                    mUsEasyRefresh.setSourceList(usBoxMovieList!!.subjects)
                    mUsEasyRefresh.setSwipeRefresh(false)
                }
    }

    inner class MovieRankNormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var movieTitle: TextView
        var moviePic: SimpleDraweeView

        init {
            movieTitle = view.findViewById(R.id.movie_title)
            moviePic = view.findViewById(R.id.movie_pic)
        }
    }

    private fun skipToMovieDetailPage(movieId: String) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.KEY_MOVIE_ID, movieId)
        startActivity(intent)
    }
}