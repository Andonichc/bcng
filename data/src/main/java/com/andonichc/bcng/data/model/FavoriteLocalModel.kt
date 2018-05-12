package com.andonichc.bcng.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteLocalModel(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @ColumnInfo(name = "name")
        var name: String = "",
        @ColumnInfo(name = "icon")
        var icon: String = "",
        @ColumnInfo(name="stationsIds")
        var stationsIds: String = "")