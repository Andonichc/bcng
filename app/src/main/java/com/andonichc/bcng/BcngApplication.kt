package com.andonichc.bcng

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.multidex.MultiDex
import com.andonichc.bcng.di.ActivityInjector
import com.andonichc.bcng.di.ActivityInjectorImpl
import com.andonichc.bcng.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class BcngApplication : Application(), HasActivityInjector {

    var injector: ActivityInjector = ActivityInjectorImpl()
        @VisibleForTesting(otherwise = Context.MODE_PRIVATE)
        set

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}