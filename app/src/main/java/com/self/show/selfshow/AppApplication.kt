package com.self.show.selfshow

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by guolei on 18-2-3.
 */
class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}