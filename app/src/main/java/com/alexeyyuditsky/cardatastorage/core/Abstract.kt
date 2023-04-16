package com.alexeyyuditsky.cardatastorage.core

import android.database.sqlite.SQLiteException
import com.alexeyyuditsky.cardatastorage.R

interface Abstract {

    interface DataToDomain<T> {

        fun map(): T

        fun errorType(e: Exception): ErrorType {
            return when (e) {
                is SQLiteException -> ErrorType.NO_DATABASE
                else -> ErrorType.GENERIC_ERROR
            }
        }

    }

    interface DomainToUi<T> {

        fun map(resourceProvider: ResourceProvider): T

        fun errorMessage(errorType: ErrorType, resourceProvider: ResourceProvider): String {
            val message = when (errorType) {
                ErrorType.NO_DATABASE -> R.string.no_database
                else -> R.string.something_went_wrong
            }
            return resourceProvider.getString(message)
        }
    }


}