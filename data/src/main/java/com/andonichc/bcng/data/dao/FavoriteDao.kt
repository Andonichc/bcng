package com.andonichc.bcng.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.andonichc.bcng.data.model.FavoriteLocalModel
import io.reactivex.Single


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Single<List<FavoriteLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteLocalModel: FavoriteLocalModel)
}