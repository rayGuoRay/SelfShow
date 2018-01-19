package com.self.show.selfshow.network

import com.self.show.selfshow.bean.MovieDetail
import com.self.show.selfshow.bean.MovieList
import com.self.show.selfshow.bean.UsBoxMovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by guolei on 18-1-16.
 */
interface NetwrokApi {
    /**
     * 搜索电影
     */
    @GET("movie/search")
    fun searchMovie(@QueryMap paramMap: Map<String, String>): Observable<MovieList>

    /**
     * 获取北美票房排行榜
     */
    @GET("movie/us_box")
    fun getUsBox(): Observable<UsBoxMovieList>

    /**
     * 获取排名250
     */
    @GET("movie/top250")
    fun getTop250(@Query("start") start: Int, @Query("count") count: Int): Observable<MovieList>

    /**
     * 获取电影详情{使用占位符替换}
     */
    @GET("movie/subject/{id}")
    fun getSubjectDetail(@Path("id") id: String): Observable<MovieDetail>
}