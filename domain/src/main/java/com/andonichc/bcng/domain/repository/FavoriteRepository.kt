package com.andonichc.bcng.domain.repository

import com.andonichc.bcng.domain.model.FavoriteModel
import io.reactivex.Single


interface FavoriteRepository {
    fun getFavorites(): Single<List<FavoriteModel>>

    fun addFavorite(favorite: FavoriteModel)
}