package com.andonichc.bcng.domain.usecase

import com.andonichc.bcng.domain.AppSchedulers
import com.andonichc.bcng.domain.model.StationModel
import com.andonichc.bcng.domain.repository.BikeRepository
import io.reactivex.Single
import javax.inject.Inject


class GetStationsUseCase
@Inject constructor(private val bikeRepository: BikeRepository,
                    private val schedulers: AppSchedulers) {

    fun execute(): Single<List<StationModel>> =
            bikeRepository.getBikes()
                    .observeOn(schedulers.main)
                    .subscribeOn(schedulers.io)

    fun execute(lat: Double, lon: Double): Single<List<StationModel>> =
            execute().map {
                        orderByProximity(it, lat, lon)
                    }

    private fun orderByProximity(list: List<StationModel>, lat: Double, lon: Double): List<StationModel> {
        list.forEach {
            it.distance = calculateDistance(it.latitude, it.longitude, lat, lon)
        }
        return list.sortedBy(StationModel::distance)
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val earthRadius = 6371000.0 //meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return (earthRadius * c).toInt()
    }
}