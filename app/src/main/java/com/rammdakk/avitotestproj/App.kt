package com.rammdakk.avitotestproj

import android.app.Application
import android.content.Context
import com.rammdakk.avitotestproj.connectivity.ConnectivityModule
import com.rammdakk.todo.ioc.ApplicationComponent

class App : Application() {

    val applicationComponent: ApplicationComponent =
        DaggerApplicationComponent.builder().databaseModule(DatabaseModule(this))
            .connectivityModule(
                ConnectivityModule(this)
            ).build()

    override fun onCreate() {
        applicationComponent.injectTo(this)
        super.onCreate()
    }


    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}