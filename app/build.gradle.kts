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
}

android {
    namespace = "com.suatzengin.iloveanimals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.suatzengin.iloveanimals"
        minSdk = 24
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

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)

    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

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
    implementation("io.coil-kt:coil-compose:2.5.0")

    // CameraX
    implementation(libs.bundles.cameraX)

    // Google Maps SDK - Location
    implementation(libs.googleMaps)
    implementation(libs.play.services.location)

    // Lottie animation
    implementation(libs.lottie)

    androidTestImplementation(composeBom)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}