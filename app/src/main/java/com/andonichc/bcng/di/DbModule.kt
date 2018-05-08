package com.andonichc.bcng.di

import android.content.Context
import com.andonichc.bcng.data.dao.FavoriteDao
import com.andonichc.bcng.data.dao.FavoritesDataBase
import com.andonichc.bcng.data.datasource.FavoritesLocalDataSource
import com.andonichc.bcng.data.mapper.FavoriteLocalMapper
import com.andonichc.bcng.data.mapper.FavoriteModelMapper
import com.andonichc.bcng.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun providesFavoriteDao(context: Context): FavoriteDao =
            FavoritesDataBase.Builder()
                    .buildFavoriteDao(context)

    @Provides
    @Singleton
    fun providesFavoriteRepository(favoriteDao: FavoriteDao,
                                   localMapper: FavoriteLocalMapper,
                                   mapper: FavoriteModelMapper): FavoriteRepository =
            FavoritesLocalDataSource(favoriteDao, localMapper, mapper)
}