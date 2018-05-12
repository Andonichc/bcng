package com.andonichc.bcng.data.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.andonichc.bcng.data.model.FavoriteLocalModel

const val FAVORITES_DB = "favorites.db"

@Database(entities = [FavoriteLocalModel::class], version = 1)
abstract class FavoritesDataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    class Builder {
        fun buildFavoriteDao(context: Context): FavoriteDao =
                Room.databaseBuilder(context.applicationContext,
                        FavoritesDataBase::class.java, FAVORITES_DB)
                        .build()
                        .favoriteDao()

    }

}