package com.self.show.selfshow.network

import com.self.show.selfshow.config.AppConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * A retrofit singleton
 * Created by guolei on 18-1-16.
 */
class RetrofitWrapper private constructor() {

    private val defaultTimeout: Long = 15L
    val mNetWorkApi: NetwrokApi

    init {
        val interceptor = HttpLoggingInterceptor()
//        if (AppConfig.LOG_DEBUG)
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        else
//            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
                .readTimeout(defaultTimeout, TimeUnit.SECONDS)
                .build()
        val retrofit: Retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        mNetWorkApi = retrofit.create(NetwrokApi::class.java)
    }

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitWrapper()
        }
    }
}