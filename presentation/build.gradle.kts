plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
        targetSdk = 31
        multiDexEnabled = true

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    val koinVersion = "3.1.5"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha05")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("io.insert-koin:koin-android:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")

    implementation("com.jakewharton.timber:timber:5.0.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    implementation(project(":resources"))
    implementation(project(":common"))
    implementation(project(":domain"))
}
