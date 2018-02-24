package com.self.show.selfshow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.self.show.selfshow.network.RetrofitWrapper
import com.self.show.selfshow.utils.LogUtils
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by guolei on 18-1-17.
 */
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MOVIE_DETAIL"
        public val KEY_MOVIE_ID = "movie_id"
    }

    private lateinit var mMovieTitle: TextView
    private lateinit var mMoviePoster: SimpleDraweeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvdetail)
        initView()
        getMovieDetail(intent.getStringExtra(KEY_MOVIE_ID))
    }

    private fun initView() {
//        mMovieTitle = findViewById(R.id.component_title) as TextView
        mMoviePoster = findViewById(R.id.poster_image) as SimpleDraweeView
    }

    private fun getMovieDetail(movieId: String) {
        // 获取某部电影评分详情
        val movieDetailObserver = RetrofitWrapper.instance.mNetWorkApi.getSubjectDetail(movieId)
        movieDetailObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError{ onError -> LogUtils.d(TAG, "request onError:" + onError.message)}
                .subscribe { movieDetail ->
//                    mMovieTitle.text = movieDetail.title
                    mMoviePoster.setImageURI(movieDetail.images.large)

                    LogUtils.d("raytest", "movie detail title:" + movieDetail.title)
                    LogUtils.d("raytest", "movie detail summary:" + movieDetail.summary)
                    LogUtils.d("raytest", "movie detail images small:" + movieDetail.images.small)
                    LogUtils.d("raytest", "movie detail images medium:" + movieDetail.images.medium)
                    LogUtils.d("raytest", "movie detail images large:" + movieDetail.images.large)
                }
    }
}