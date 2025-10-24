plugins {
    id("com.android.application") version "8.2.2"
    id("org.jetbrains.kotlin.android") version "1.9.10"
    id("org.jetbrains.kotlin.kapt") version "1.9.10"
    id("com.google.dagger.hilt.android") version "2.52"
}

android {
    namespace = "com.akistudio.feature.filemanager"
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

    // SAF + DocumentFile
    implementation("androidx.documentfile:documentfile:1.0.1")

    // Zip/unzip
    implementation("net.lingala.zip4j:zip4j:2.11.5")
}
