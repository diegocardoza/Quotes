import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.example.quotes"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.quotes"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.quotes.di.HiltTestRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(localProperties.getProperty("RELEASE_STORE_FILE") as String)
            storePassword = localProperties.getProperty("RELEASE_STORE_PASSWORD") as String
            keyAlias = localProperties.getProperty("RELEASE_KEY_ALIAS") as String
            keyPassword = localProperties.getProperty("RELEASE_KEY_PASSWORD") as String
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "application_name", "Quotes")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProperties.getProperty("BASE_URL_PROD")}\""
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".dev"
            isMinifyEnabled = false
            isDebuggable = true
            resValue("string", "application_name", "[DEV] Quotes")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${localProperties.getProperty("BASE_URL_DEV")}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // API for Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Dagger hilt for DI
    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    ksp(libs.dagger.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.junit)

    // Test Coroutine
    testImplementation(libs.kotlinx.coroutines.test)

    // Google Truth
    testImplementation(libs.truth)

    // MockK
    testImplementation(libs.mockk)

    // Turbine
    testImplementation(libs.turbine)

    // MockWebserver
    testImplementation(libs.mockwebserver)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.mockwebserver)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}