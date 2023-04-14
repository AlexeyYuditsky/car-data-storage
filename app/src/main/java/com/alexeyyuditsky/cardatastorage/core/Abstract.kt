package com.alexeyyuditsky.cardatastorage.core

import com.alexeyyuditsky.cardatastorage.R
import java.net.UnknownHostException
import com.bumptech.glide.load.HttpException

interface Abstract {

    interface DataToDomain<T> {

        fun map(): T

        fun errorType(e: Exception): ErrorType {
            return when (e) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                else -> ErrorType.GENERIC_ERROR
            }
        }

    }

    interface DomainToUi<T> {

        fun map(resourceProvider: ResourceProvider): T

        fun errorMessage(errorType: ErrorType, resourceProvider: ResourceProvider): String {
            val message = when (errorType) {
                ErrorType.NO_CONNECTION -> R.string.no_connection_message
                ErrorType.SERVICE_UNAVAILABLE -> R.string.service_unavailable_message
                else -> R.string.something_went_wrong
            }
            return resourceProvider.getString(message)
        }
    }


}