plugins {
    id("com.android.application") version "8.2.2"
    id("org.jetbrains.kotlin.android") version "1.9.10"
    id("com.google.dagger.hilt.android") version "2.52"
    id("org.jetbrains.kotlin.kapt") version "1.9.10"
}

tasks.named<Delete>("clean") {
    delete(layout.buildDirectory)
}
