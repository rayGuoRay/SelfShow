package com.self.show.selfshow.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.self.show.selfshow.R
import com.self.show.selfshow.base.BaseFragment
import com.self.show.selfshow.bean.MovieList
import com.self.show.selfshow.bean.MovieSubject
import com.self.show.selfshow.network.RetrofitWrapper
import com.self.show.selfshow.widget.EasyListRefreshView
import com.self.show.selfshow.widget.EasyRefreshAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by guolei on 18-1-22.
 */
class AllRankFragment: BaseFragment() {

    private lateinit var mEasyList: EasyListRefreshView
    private lateinit var mRankList: List<MovieSubject>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_allrank, container, false)
        mEasyList = rootView.findViewById(R.id.all_rank_easy_refresh)
        return rootView
    }

    override fun loadData() {
        mEasyList.bindAdapter(object: EasyRefreshAdapter.EasyHolderCallback{
            override fun onCreateNormal(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
                return MovieRankNormalViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_normal, parent, false))
            }

            override fun onBindNormal(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as MovieRankNormalViewHolder).movieTitle.text = mRankList[position].title
                (holder as MovieRankNormalViewHolder).moviePic.setImageURI(mRankList[position].images.large)
            }
        })
        mEasyList.setSlideCallback(object: EasyListRefreshView.SlideCallback{
            override fun onSlideToTopLoad() {
                onTopLoadStart()
            }

            override fun onSlideToBottomLoad(lastPosition: Int) {

            }
        })
        mEasyList.setSwipeRefresh(true)
        onTopLoadStart()
    }


    private fun onTopLoadStart() {
        val rankMovieList = RetrofitWrapper.instance.mNetWorkApi.getTop250(0, 10)
        rankMovieList.map { movieList: MovieList? -> movieList!!.subjects }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movieList ->
                    mEasyList.setSourceList(movieList)
                    mRankList = movieList
                    mEasyList.setSwipeRefresh(false)
                }
    }

    inner class MovieRankNormalViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var movieTitle: TextView
        var moviePic: SimpleDraweeView

        init {
            movieTitle = view.findViewById(R.id.movie_title)
            moviePic = view.findViewById(R.id.movie_pic)
        }
    }
}