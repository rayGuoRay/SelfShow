package com.self.show.selfshow

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.self.show.selfshow.network.RetrofitWrapper
import com.self.show.selfshow.utils.LogUtils
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.os.Build
import android.view.View


/**
 * Created by guolei on 18-1-17.
 */
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MOVIE_DETAIL"
        public val KEY_MOVIE_ID = "movie_id"
    }

    private lateinit var mMoviePoster: SimpleDraweeView
    private lateinit var mMovieTitle: TextView
    private lateinit var mMovieStar: TextView
    private lateinit var mMovieSummary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvdetail)
        initView()
        getMovieDetail(intent.getStringExtra(KEY_MOVIE_ID))
    }

    private fun initView() {
        mMoviePoster = findViewById(R.id.poster_image) as SimpleDraweeView
        mMovieTitle = findViewById(R.id.mv_title_text) as TextView
        mMovieStar = findViewById(R.id.mv_star_text) as TextView
        mMovieSummary = findViewById(R.id.mv_summary_text) as TextView
    }

    private fun getMovieDetail(movieId: String) {
        // 获取某部电影评分详情
        val movieDetailObserver = RetrofitWrapper.instance.mNetWorkApi.getSubjectDetail(movieId)
        movieDetailObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError{ onError -> LogUtils.d(TAG, "request onError:" + onError.message)}
                .subscribe { movieDetail ->
                    mMoviePoster.setImageURI(movieDetail.images.large)
                    mMovieTitle.text = movieDetail.title
                    mMovieStar.text = movieDetail.rating.average.toString()
                    mMovieSummary.text = movieDetail.summary
                }
    }
}