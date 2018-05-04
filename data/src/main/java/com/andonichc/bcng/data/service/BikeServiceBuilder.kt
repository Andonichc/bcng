package com.andonichc.bcng.data.service

import javax.xml.ws.Service


class BikeServiceBuilder : ServiceBuilder<BikeService>() {

    override fun build(): BikeService =
            createRetrofit().create(BikeService::class.java)
}