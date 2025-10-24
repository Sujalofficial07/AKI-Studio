// No plugins block here

tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}
