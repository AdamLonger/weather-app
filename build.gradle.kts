
buildscript {
    val kotlinVersion = "1.5.30"
    repositories {
        mavenCentral()
        google()
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
    }
}

plugins {
    id("com.android.application") version "7.1.0" apply false
    id("com.android.library") version "7.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.5.30" apply false
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
}

subprojects {
    apply<io.gitlab.arturbosch.detekt.DetektPlugin>()

    detekt.apply {
        buildUponDefaultConfig = true
        parallel = true
        autoCorrect = true

        config.setFrom(files(rootProject.rootDir.resolve("./config/detekt/detekt.yml")))
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.17.1")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}