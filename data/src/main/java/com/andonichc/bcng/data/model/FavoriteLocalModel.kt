package com.andonichc.bcng.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteLocalModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "icon")
        val icon: String,
        @ColumnInfo(name="stationsIds")
        val stationsIds: String)