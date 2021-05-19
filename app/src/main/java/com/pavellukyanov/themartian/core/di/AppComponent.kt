package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.core.application.App
import com.pavellukyanov.themartian.core.di.module.*
import com.pavellukyanov.themartian.ui.MainActivity
import com.pavellukyanov.themartian.ui.main.mainpage.MainFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepoModule::class,
        RouterModule::class,
        ViewModelModule::class,
        WorkerModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)
}