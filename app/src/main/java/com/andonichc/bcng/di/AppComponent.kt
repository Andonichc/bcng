package com.andonichc.bcng.di

import com.andonichc.bcng.BcngApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ActivityBuilder::class,
    AndroidInjectionModule::class,
    NetModule::class,
    DbModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BcngApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: BcngApplication)
}