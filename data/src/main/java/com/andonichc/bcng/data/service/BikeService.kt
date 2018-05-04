package com.andonichc.bcng.data.service

import com.andonichc.bcng.data.model.StationsApiResponse
import io.reactivex.Single
import retrofit2.http.GET


interface BikeService {

    @GET("/v2/stations")
    fun getStations(): Single<StationsApiResponse>
}