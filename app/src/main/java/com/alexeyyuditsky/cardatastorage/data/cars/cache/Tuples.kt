package com.alexeyyuditsky.cardatastorage.data.cars.cache

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class UpdateCarTuple(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "hp") val hp: Int,
)

data class NewCarTuple(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "hp") val hp: Int,
)