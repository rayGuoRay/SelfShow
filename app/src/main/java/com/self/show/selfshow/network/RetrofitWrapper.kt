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
        if (AppConfig.LOG_DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
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

//        @Volatile
//        private var instance: RetrofitWrapper? = null

//        fun getInstance(): RetrofitWrapper {
//            return null;
//        }


//        @Volatile
//
//
//        fun getInstance(): RetrofitWrapper {
//            if (instance == null) {
//                synchronized(RetrofitWrapper::class) {
//                    if (instance == null) {
//                        instance = RetrofitWrapper()
//                    }
//                }
//            }
//            return instance!!
//        }
    }

//    private var retrofit: Retrofit = initRetrofit()
//
//    private object Holder { val INSTANCE = RetrofitWrapper() }
//
//    companion object{
//        val instance: RetrofitWrapper by lazy { Holder.INSTANCE }
//    }
//
//    private fun initRetrofit(): Retrofit {
//        val interceptor = HttpLoggingInterceptor()
//        if (AppConfig.LOG_DEBUG)
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        else
//            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
//        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
//                .addInterceptor(HttpLoggingInterceptor().setLevel(interceptor))
//                .retryOnConnectionFailure(true)
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .readTimeout(1000, TimeUnit.SECONDS)
//                .build()
//        return  Retrofit.Builder()
//                .baseUrl("")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build()
//    }
}