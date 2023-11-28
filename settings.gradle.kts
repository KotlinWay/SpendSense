rootProject.name = "SpendSense"
include(":shared")
include(":desktop")
include(":android")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}