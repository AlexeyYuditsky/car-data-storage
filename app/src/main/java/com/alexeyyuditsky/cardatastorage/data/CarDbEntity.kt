package com.alexeyyuditsky.cardatastorage.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars",)
data class CarDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "hp") val hp: Int,
    @ColumnInfo(name = "image") val image: String?,
)
