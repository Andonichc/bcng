package com.andonichc.bcng.domain.repository

import com.andonichc.bcng.domain.model.StationModel
import io.reactivex.Single


interface BikeRepository {
    fun getBikes(forceRefresh: Boolean = false): Single<List<StationModel>>
}