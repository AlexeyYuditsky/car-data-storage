package com.alexeyyuditsky.cardatastorage.presentation.cars

sealed class CarUi {

    open fun map(mapper: TextMapper) = Unit

    data class Base(
        private val id: Long,
        private val model: String,
        private val color: String,
        private val speed: Int,
        private val hp: Int,
        private val image: String?,
    ) : CarUi() {
        override fun map(mapper: TextMapper) {
            mapper.map(model, color, speed, hp, image)
        }
    }

    data class Fail(
        val message: String,
    ) : CarUi() {
        override fun map(mapper: TextMapper) {
            mapper.map(message)
        }
    }

    object Progress : CarUi()

    object Empty : CarUi()

}
