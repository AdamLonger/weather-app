plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
        targetSdk = 31
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("androidx.room:room-common:2.4.2")

    implementation("io.insert-koin:koin-android:3.1.5")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation(project(":resources"))
    implementation(project(":common"))
}
