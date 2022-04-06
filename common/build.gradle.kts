
plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
        targetSdk = 31
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha05")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation(project(":resources"))
}
