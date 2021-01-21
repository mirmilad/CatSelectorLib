package com.gini.catselectorlib.di

import android.content.Context

open class ServiceLocator {
    interface IService {}

    companion object {
        private val servicesInstances = HashMap<String, Any>()
        private val services = HashMap<String, Class<*>>()
        private lateinit var context: Context

        private val lock = Object()

        fun initialize(context: Context) {
            servicesInstances.clear()
            services.clear()
            this.context = context.applicationContext
        }

        fun <T> get(clazz: Class<T>): T {
            return getService(clazz.name) as T
        }

        fun bindCustomServiceImplementation(
            interfaceClass: Class<*>,
            serviceImplementation: Class<*>
        ) {
            synchronized(lock) {
                servicesInstances.remove(interfaceClass.name)
                services.put(interfaceClass.name, serviceImplementation)
            }
        }

        fun <T> bindCustomServiceInstance(interfaceClass: Class<T>, serviceInstance: T) {
            synchronized(lock) {
                servicesInstances.put(interfaceClass.name, serviceInstance as Any)
            }
        }

        private fun getService(name: String): Any {
            synchronized(lock) {
                val service = servicesInstances[name]
                service?.let {
                    return it
                }

                try {
                    var serviceInstance: Any?
                    val clazz: Class<*>?
                    if (services.containsKey(name))
                        clazz = services[name]
                    else
                        clazz = Class.forName(name)

                    try {
                        serviceInstance =
                            clazz!!.getConstructor(Context::class.java).newInstance(context)
                    } catch (ex: NoSuchMethodException) {
                        serviceInstance = clazz!!.getConstructor().newInstance()
                    }

                    if (serviceInstance !is IService)
                        throw IllegalArgumentException("Requested service must implement IService interface")

                    servicesInstances[name] = serviceInstance
                    return serviceInstance
                } catch (e: ClassNotFoundException) {
                    throw java.lang.IllegalArgumentException(
                        "Requested service class was not found: $name",
                        e
                    )
                } catch (e: Exception) {
                    throw java.lang.IllegalArgumentException(
                        "Cannot initialize requested service: $name",
                        e
                    )
                }
            }
        }
    }
}