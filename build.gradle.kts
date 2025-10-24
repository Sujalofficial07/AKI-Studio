plugins {
    // Managed per-module
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
