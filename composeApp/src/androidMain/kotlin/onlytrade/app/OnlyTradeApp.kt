package onlytrade.app

import android.app.Application
import android.content.Context

class OnlyTradeApp : Application() {


    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}