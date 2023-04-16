package com.alexeyyuditsky.cardatastorage.core

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class AbstractTest {

    @Test
    fun test_success() {
        val dataObject = DataObjectTest.Base(listOf())

        val domainObject = dataObject.map()
        val expected = DomainObjectTest.Base(listOf())

        assertEquals(expected, domainObject)
    }

    @Test
    fun test_is_success() {
        val dataObject = DataObjectTest.Base(listOf())

        val domainObject = dataObject.map()

        assertTrue(domainObject is DomainObjectTest.Base)
    }

    @Test
    fun test_fail() {
        val exception = Exception()
        val dataObject = DataObjectTest.Fail(exception)

        val domainObject = dataObject.map()
        val expected = DomainObjectTest.Fail(exception)

        assertEquals(expected, domainObject)
    }

    @Test
    fun test_is_fail() {
        val dataObject = DataObjectTest.Fail(Exception())

        val domainObject = dataObject.map()

        assertTrue(domainObject is DomainObjectTest.Fail)
    }

    sealed class DataObjectTest : Abstract.DataToDomain<DomainObjectTest> {
        data class Base(
            private val list: List<String>,
        ) : DataObjectTest() {
            override fun map(): DomainObjectTest {
                return DomainObjectTest.Base(list)
            }
        }

        data class Fail(
            private val e: Exception,
        ) : DataObjectTest() {
            override fun map(): DomainObjectTest {
                return DomainObjectTest.Fail(e)
            }
        }
    }

    sealed class DomainObjectTest : Abstract.DomainToUi<UiObjectTest> {
        data class Base(
            private val list: List<String>,
        ) : DomainObjectTest() {
            override fun map(resourceProvider: ResourceProvider): UiObjectTest {
                return UiObjectTest(list)
            }
        }

        data class Fail(
            private val e: Exception,
        ) : DomainObjectTest() {
            override fun map(resourceProvider: ResourceProvider): UiObjectTest {
                return UiObjectTest(listOf(e.toString()))
            }
        }
    }

    data class UiObjectTest(
        private val list: List<String>,
    )

}