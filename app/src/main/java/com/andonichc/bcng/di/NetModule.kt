package com.andonichc.bcng.di

import com.andonichc.bcng.BuildConfig
import com.andonichc.bcng.data.cache.StationsMemoryCache
import com.andonichc.bcng.data.datasource.BikeApiDataSource
import com.andonichc.bcng.data.mapper.StationApiMapper
import com.andonichc.bcng.data.service.BikeService
import com.andonichc.bcng.data.service.BikeServiceBuilder
import com.andonichc.bcng.domain.repository.BikeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetModule {

    @Provides
    @Singleton
    fun providesBikeService(): BikeService =
            BikeServiceBuilder()
                    .logging(BuildConfig.DEBUG)
                    .build()

    @Provides
    @Singleton
    fun providesBikeRespository(bikeService: BikeService, mapper: StationApiMapper,
                                memoryCache: StationsMemoryCache): BikeRepository =
            BikeApiDataSource(bikeService, mapper, memoryCache)
}