package com.alexeyyuditsky.cardatastorage.presentation.cars

interface TextMapper {
    fun map(model: String, color: String = "", speed: Int = 0, hp: Int = 0, image: String? = null)
}