package com.andonichc.bcng.data.datasource

import com.andonichc.bcng.data.cache.StationsMemoryCache
import com.andonichc.bcng.data.mapper.StationApiMapper
import com.andonichc.bcng.data.service.BikeService
import com.andonichc.bcng.domain.model.StationModel
import com.andonichc.bcng.domain.repository.BikeRepository
import io.reactivex.Single
import javax.inject.Inject


class BikeApiDataSource
@Inject constructor(private val bikeService: BikeService,
                    private val mapper: StationApiMapper,
                    private val memoryCache: StationsMemoryCache) : BikeRepository {
    override fun getBikes(forceRefresh: Boolean): Single<List<StationModel>> {

        if (memoryCache.isValid() && !forceRefresh) {
            return Single.just(memoryCache.stationsList)
        }
        return bikeService.getStations()
                .map { it.stations }
                .map(mapper::map)
                .doOnSuccess { memoryCache.stationsList = it }
    }
}