# Weather Something

An Android development task
Created by Hosszú Ádám

A demo weather information application using the openweathermap.org API.

---

To run the application an OpenWeather APIKey must be added to the local.properties file:

OPEN_WEATHER_KEY= <--KEY-->

---

Functional Requirements:
* Get User Location
* Request weather data from OpenWeatherMap API
* Store data in a local database

Non-Functional Requirements:
* Compatibility with Android 4.1 and onwards
* Code quality, readability and consistent code style
* Best UI practices (Material design)
* Local data storage
* Unit test (JUnit/Robolectric) -> ViewModel Test

Optinal Requriement:
* Add "More Details" Fragment
* Add Screen transition
* Implement Periodic updates
* Create ContentProvider
* Implement CRUD operations
* Use Kotlin

---

Used Libraries:
* Kotlin Serializaton
* GMS Services (FusedLocation)
* Lifecycle ViewModel
* Koin (DI)
* Jetpack Navigation + SafeArgs
* Room (Local SQL Database)
* Retrofit2 (HTTP Client)
* FastAdapter (RecyclerView Helper)
* Timber (Logging)
* LeakCanary
* Detekt (Code Quality)
* JUnit
* Kotlin Coroutines Test
