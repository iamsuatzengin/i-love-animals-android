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
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

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

    // CameraX
    implementation(libs.bundles.cameraX)

    implementation(libs.googleMaps)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}