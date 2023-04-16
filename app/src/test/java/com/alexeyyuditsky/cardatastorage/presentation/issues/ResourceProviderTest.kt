package com.alexeyyuditsky.cardatastorage.presentation.issues

import org.junit.Test
import org.junit.Assert.*
import com.alexeyyuditsky.cardatastorage.R
import com.alexeyyuditsky.cardatastorage.core.ResourceProvider

class ResourceProviderTest {

    @Test
    fun test() {
        val resourceProviderTest = ResourceProviderTest()

        val actual = resourceProviderTest.getString(R.string.no_database)
        val expected = "database_is_unavailable"

        assertEquals(expected, actual)
    }

    private class ResourceProviderTest : ResourceProvider {
        override fun getString(id: Int): String {
            return when (id) {
                R.string.no_database -> "database_is_unavailable"
                else -> "something_went_wrong"
            }
        }
    }

}