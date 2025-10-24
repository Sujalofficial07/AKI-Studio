plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.akistudio.feature.builder"
    compileSdk = 35
    defaultConfig { minSdk = 24 }

    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = project.findProperty("composeCompilerVersion") as String
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions { jvmTarget = "21" }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:${project.findProperty("composeBom")}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}
