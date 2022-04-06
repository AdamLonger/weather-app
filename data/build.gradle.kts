
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    val roomVersion = "2.4.2"

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("io.insert-koin:koin-android:3.1.5")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")


    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation(project(":resources"))
    implementation(project(":common"))
    implementation(project(":domain"))
}
