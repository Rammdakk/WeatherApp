package com.rammdakk.todo.ioc

import com.rammdakk.avitotestproj.App
import com.rammdakk.avitotestproj.connectivity.ConnectivityModule
import com.rammdakk.avitotestproj.ioc.ViewModelFactory


@ApplicationComponentScope
@dagger.Component(modules = [RetrofitModule::class, ConnectivityModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun injectTo(application: App)
}
