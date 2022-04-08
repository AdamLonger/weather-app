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
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha05")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("io.insert-koin:koin-android:3.1.5")

    implementation("com.jakewharton.timber:timber:5.0.1")

    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.ext:truth:1.4.0")

    androidTestImplementation("io.insert-koin:koin-test:3.1.5")
    androidTestImplementation("io.mockk:mockk-android:1.12.0")

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    implementation(project(":resources"))
    implementation(project(":common"))
    implementation(project(":domain"))
}
