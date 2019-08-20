package katas.client.twitter.ui

import android.app.Application
import katas.client.twitter.BuildConfig
import timber.log.Timber.DebugTree
import timber.log.Timber.plant

class TwitterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            plant(DebugTree())
        }

    }
}