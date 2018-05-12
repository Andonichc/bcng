package com.andonichc.bcng.data.dao

import android.arch.persistence.room.*
import com.andonichc.bcng.data.model.FavoriteLocalModel
import io.reactivex.Completable
import io.reactivex.Single


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Single<List<FavoriteLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteLocalModel: FavoriteLocalModel)

    @Delete
    fun deleteFavorite(favoriteLocalModel: FavoriteLocalModel)
}