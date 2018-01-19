package com.self.show.selfshow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.self.show.selfshow.bean.MovieList
import com.self.show.selfshow.bean.MovieSubject
import com.self.show.selfshow.bean.UsBoxMovie
import com.self.show.selfshow.bean.UsBoxMovieList
import com.self.show.selfshow.network.RetrofitWrapper
import com.self.show.selfshow.utils.LogUtils
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    private fun getData() {
        // 获取某部电影评分详情
        val movieDetailObserver = RetrofitWrapper.instance.mNetWorkApi.getSubjectDetail("26586766")
        movieDetailObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { onError ->
                    LogUtils.d("raytest", "request onError:" + onError.message);
                }
                .subscribe { movieDetail ->
                    LogUtils.d("raytest", "movie detail title:" + movieDetail.title)
                    LogUtils.d("raytest", "movie detail summary:" + movieDetail.summary)
                    LogUtils.d("raytest", "movie detail images small:" + movieDetail.images.small)
                    LogUtils.d("raytest", "movie detail images medium:" + movieDetail.images.medium)
                    LogUtils.d("raytest", "movie detail images large:" + movieDetail.images.large)
                }

        // 获取电影排行前250名
        val rankMovieList = RetrofitWrapper.instance.mNetWorkApi.getTop250(0, 10)
        rankMovieList.map { movieList: MovieList? -> movieList!!.subjects }
                .flatMap { subjects: List<MovieSubject>? -> Observable.from(subjects)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { subject ->
                    LogUtils.d("raytest", "movie detail title:" + subject.id)
                    LogUtils.d("raytest", "movie detail summary:" + subject.title)
                }

        // 获取全美电影排行榜
        val usRankBox = RetrofitWrapper.instance.mNetWorkApi.getUsBox()
        usRankBox.flatMap { boxMovieList: UsBoxMovieList? -> Observable.from(boxMovieList!!.subjects) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { usBoxMovie: UsBoxMovie? ->
                    LogUtils.d("raytest", "usBoxMovie rank:" + usBoxMovie!!.rank)
                    LogUtils.d("raytest", "usBoxMovie date:" + usBoxMovie.date)
                    LogUtils.d("raytest", "usBoxMovie subject:" + usBoxMovie.subject.title)
                }

        // 搜索电影
        val paramSearchMap: HashMap<String, String> = hashMapOf<String, String>()
        paramSearchMap.put("q", "黑色")
        val searchObservable = RetrofitWrapper.instance.mNetWorkApi.searchMovie(paramSearchMap)
        searchObservable.map { movieList: MovieList? -> movieList!!.subjects }
                .flatMap { subjects: List<MovieSubject>? -> Observable.from(subjects) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { moviewSubject: MovieSubject? ->
                    LogUtils.d("raytest", "usBoxMovie Search Movie:" + moviewSubject!!.title)
                }
    }
}
