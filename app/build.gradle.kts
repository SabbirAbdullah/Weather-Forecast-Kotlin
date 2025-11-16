plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "com.weatherforecast.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.weatherforecast.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // BuildConfig field for API key
        buildConfigField(
            "String",
            "OPENWEATHER_API_KEY",
            "\"${project.properties["OPENWEATHER_API_KEY"] ?: "YOUR_API_KEY"}\""
        )
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

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        // Compose Compiler included automatically via BOM
        kotlinCompilerExtensionVersion = "1.6.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // ------------------------------------------------
    // Android Core
    // ------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ------------------------------------------------
    // Compose + BOM
    // ------------------------------------------------
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)

    // ------------------------------------------------
    // Lifecycle + ViewModel
    // ------------------------------------------------
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.ktx)

    // ------------------------------------------------
    // Coroutines
    // ------------------------------------------------
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // ------------------------------------------------
    // Retrofit + OkHttp
    // ------------------------------------------------
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // ------------------------------------------------
    // Coil (Compose Images)
    // ------------------------------------------------
    implementation(libs.coil.compose)

    // ------------------------------------------------
    // Hilt DI
    // ------------------------------------------------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    // ------------------------------------------------
    // Tests
    // ------------------------------------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
