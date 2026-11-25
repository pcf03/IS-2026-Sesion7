plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "es.uniovi.converter"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "es.uniovi.converter"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    // Retrofit: cliente HTTP que convierte una interfaz Kotlin en llamadas REST
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Conversor JSON <-> objetos Kotlin (usa Gson por debajo)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}