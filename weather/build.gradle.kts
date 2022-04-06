
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
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
    val navVersion = "2.4.1"
    val fastAdapter = "5.6.0"

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    api("androidx.navigation:navigation-fragment-ktx:$navVersion")
    api("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("com.google.android.gms:play-services-location:19.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha05")

    implementation("io.insert-koin:koin-android:3.1.5")

    implementation("com.mikepenz:fastadapter:$fastAdapter")
    implementation("com.mikepenz:fastadapter-extensions-binding:$fastAdapter")
    implementation("com.mikepenz:fastadapter-extensions-diff:$fastAdapter")

    implementation("com.jakewharton.timber:timber:5.0.1")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.8.1")

    implementation(project(":resources"))
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":presentation"))
}
