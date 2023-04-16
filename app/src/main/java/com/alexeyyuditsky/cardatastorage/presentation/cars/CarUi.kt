package com.alexeyyuditsky.cardatastorage.presentation.cars

sealed class CarUi {

    open fun map(mapper: TextMapper) = Unit
    open fun same(item: CarUi): Boolean = false
    open fun sameContent(item: CarUi): Boolean = false

    abstract class Info(
        open val id: Long,
        private val model: String,
    ) : CarUi() {
        override fun same(item: CarUi): Boolean {
            return if (item is Base) {
                this.id == item.id
            } else {
                false
            }
        }

        override fun sameContent(item: CarUi): Boolean {
            return if (item is Base) {
                this.model == item.model
            } else {
                false
            }
        }
    }

    data class Base(
        override val id: Long,
        val model: String,
        private val color: String,
        private val speed: Int,
        private val hp: Int,
        private val image: String,
    ) : Info(id, model) {
        override fun map(mapper: TextMapper) {
            mapper.map(id, model, color, speed, hp, image)
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
