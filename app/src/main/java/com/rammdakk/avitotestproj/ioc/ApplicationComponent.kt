package com.rammdakk.avitotestproj.ioc

import com.rammdakk.avitotestproj.App
import com.rammdakk.avitotestproj.connectivity.ConnectivityModule
import com.rammdakk.avitotestproj.data.restApi.RetrofitModule
import com.rammdakk.todo.ioc.ApplicationComponentScope


@ApplicationComponentScope
@dagger.Component(modules = [RetrofitModule::class, ConnectivityModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun injectTo(application: App)
}
