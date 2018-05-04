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
}