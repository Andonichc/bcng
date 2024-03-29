package com.andonichc.bcng.domain.repository

import com.andonichc.bcng.domain.model.FavoriteModel
import io.reactivex.Completable
import io.reactivex.Single


interface FavoriteRepository {
    fun getFavorites(): Single<List<FavoriteModel>>

    fun addFavorite(favorite: FavoriteModel): Completable

    fun deleteFavorite(favorite: FavoriteModel): Completable
}