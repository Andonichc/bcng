package com.andonichc.bcng.data.service


class BikeServiceBuilder : ServiceBuilder<BikeService>() {

    override fun build(): BikeService =
            createRetrofit().create(BikeService::class.java)
}