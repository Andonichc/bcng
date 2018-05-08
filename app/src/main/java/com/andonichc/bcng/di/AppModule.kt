package com.andonichc.bcng.di

import android.content.Context
import com.andonichc.bcng.BcngApplication
import com.andonichc.bcng.data.dao.FavoritesDataBase
import com.andonichc.bcng.domain.AppSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(app: BcngApplication): Context = app

    @Provides
    @Singleton
    fun providesAppSchedulers(): AppSchedulers =
            AppSchedulers(main = AndroidSchedulers.mainThread())
}