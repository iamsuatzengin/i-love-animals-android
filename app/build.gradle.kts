import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.nav.safe.args)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.secret)
    id("io.gitlab.arturbosch.detekt") version("1.23.4")
}

android {
    namespace = "com.suatzengin.iloveanimals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.suatzengin.iloveanimals"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    secrets {
        propertiesFileName = "secrets.properties"

        defaultPropertiesFileName = "local.properties"

        ignoreList.add("keyToIgnore")

        ignoreList.add("sdk.*")
    }
}

detekt {
    toolVersion = "1.23.4"
    config.setFrom(file("../config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)

    // Android Studio Preview support
    implementation(libs.compose.ui.toolingPreview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.compose.viewModel)
    implementation (libs.compose.lifecycle.runtime)

    // Jetpack Navigation
    implementation(libs.navigationFragmentKtx)
    implementation(libs.navigationUiKtx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Ktor
    implementation(libs.bundles.ktor)

    // Jetpack DataStore
    implementation(libs.dataStore)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.storage)

    // Swipe to refresh layout
    implementation (libs.androidx.swiperefreshlayout)

    // Coil - image loading library
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // CameraX
    implementation(libs.bundles.cameraX)

    // Google Maps SDK - Location
    implementation(libs.googleMaps)
    implementation(libs.play.services.location)

    // Lottie animation
    implementation(libs.lottie)

    // Chucker
    implementation(libs.chucker)

    implementation("com.google.ai.client.generativeai:generativeai:0.1.1")
    implementation(libs.firebase.messaging)

    androidTestImplementation(platform(libs.firebase.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}