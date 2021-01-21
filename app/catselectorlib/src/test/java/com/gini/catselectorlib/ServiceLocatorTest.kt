package com.gini.catselectorlib

import android.content.Context
import com.gini.catselectorlib.di.ServiceLocator
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ServiceLocatorTest {


    private val applicationContext = mock<Context> {
        on {
            applicationContext
        } doReturn this.mock
    }
    private val context = mock<Context> {
        on {
            applicationContext
        } doReturn applicationContext
    }

    @Before
    fun setUp() {
        ServiceLocator.initialize(context)
    }

    @Test
    fun test_bindImplementation() {
        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1Impl1::class.java
        )
        ServiceLocator.bindCustomServiceImplementation(
            IService2::class.java,
            Service2Impl1::class.java
        )

        val service1 = ServiceLocator.get(IService1::class.java)
        val service2 = ServiceLocator.get(IService2::class.java)

        assert(service1 is Service1Impl1)
        assert(service2 is Service2Impl1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_implementationNotFound() {
        ServiceLocator.get(IService1::class.java)
    }

    @Test
    fun test_unregisteredService() {
        val service = ServiceLocator.get(Service1Impl1::class.java)
        assertNotNull(service)
        assert(service is Service1Impl1)
    }

    @Test
    fun test_changeService() {
        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1Impl1::class.java
        )
        val service1Impl1 = ServiceLocator.get(IService1::class.java)
        assert(service1Impl1 is Service1Impl1)

        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1Impl2::class.java
        )
        val service1Impl2 = ServiceLocator.get(IService1::class.java)
        assert(service1Impl2 is Service1Impl2)
    }

    @Test
    fun test_sameInstance() {
        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1Impl1::class.java
        )

        val service1 = ServiceLocator.get(IService1::class.java)
        val service2 = ServiceLocator.get(IService1::class.java)

        assertEquals(service1, service2)
    }

    @Test
    fun test_contextInConstructor() {
        ServiceLocator.bindCustomServiceImplementation(
            IService2::class.java,
            Service2WithContext::class.java
        )

        val service = ServiceLocator.get(IService2::class.java)
        assert(service is Service2WithContext)
    }

    @Test
    fun test_useApplicationContext() {
        ServiceLocator.bindCustomServiceImplementation(
            IService2::class.java,
            Service2WithContext::class.java
        )

        val service = ServiceLocator.get(IService2::class.java)
        assertEquals((service as Service2WithContext).context, applicationContext)
        assertNotEquals((service as Service2WithContext).context, context)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_notImplementedIService() {
        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1ImplWithoutIService::class.java
        )

        ServiceLocator.get(IService1::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_notSupportedConstructor() {
        ServiceLocator.bindCustomServiceImplementation(
            IService2::class.java,
            Service2NotSupportedConstructor::class.java
        )

        ServiceLocator.get(IService2::class.java)
    }

    @Test
    fun test_customInstance() {
        ServiceLocator.bindCustomServiceImplementation(
            IService1::class.java,
            Service1Impl1::class.java
        )
        val service1 = ServiceLocator.get(IService1::class.java)

        val customInstance = Service1Impl1()
        ServiceLocator.bindCustomServiceInstance(IService1::class.java, customInstance)
        val service2 = ServiceLocator.get(IService1::class.java)

        assertEquals(service2, customInstance)
        assertNotEquals(service1, service2)
    }

    interface IService1
    interface IService2

    class Service1Impl1 : IService1, ServiceLocator.IService
    class Service1Impl2 : IService1, ServiceLocator.IService
    class Service2Impl1 : IService2, ServiceLocator.IService
    class Service2WithContext(val context: Context) : IService2, ServiceLocator.IService
    class Service2NotSupportedConstructor(val context: Context, dummy: Int) : IService2,
        ServiceLocator.IService

    class Service1ImplWithoutIService : IService1
}

