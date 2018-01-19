package com.self.show.selfshow.utils

import android.util.Log
import com.self.show.selfshow.config.AppConfig

/**
 * Created by guolei on 18-1-16.
 */
class LogUtils {

    companion object {
        fun d(tag: String, message: String) {
            if (!AppConfig.LOG_DEBUG) {
                return
            }
            Log.d(tag, message)
        }

        fun e(tag: String, message: String) {
            if (!AppConfig.LOG_DEBUG) {
                return
            }
            Log.e(tag, message)
        }
    }
}