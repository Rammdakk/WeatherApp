package com.rammdakk.avitotestproj.connectivity

import android.app.Application
import android.content.Context
import com.rammdakk.todo.ioc.ApplicationComponentScope
import dagger.Module
import dagger.Provides

@Module
class ConnectivityModule(private val application: Application) {
    @Provides
    @ApplicationComponentScope
    fun connectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver {
        return networkConnectivityObserver
    }

    @Provides
    @ApplicationComponentScope
    fun getNetworkConnectivityObserver(): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(provideAppContext())
    }

    @Provides
    @ApplicationComponentScope
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}
