plugins {
    alias(aki.plugins.android.application)
    alias(aki.plugins.kotlin.android)
    alias(aki.plugins.kotlin.kapt)
    alias(aki.plugins.hilt)
}

tasks.named<Delete>("clean") {
    delete(layout.buildDirectory)
}
