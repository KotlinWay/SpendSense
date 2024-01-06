plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.moko.res)
    alias(libs.plugins.sqldelight)
}

kotlin {
    jvm()
    androidTarget()

    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //Compose
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                //Resources
                api(libs.resources.core)
                api(libs.resources.compose)

                //Settings
                implementation(libs.settings)

                //Di
                api(libs.koin.core)

                //Datetime
                implementation(libs.datetime)

                //Sqldelight
                implementation(libs.sqldelight.coroutines.extensions)
            }
        }

        androidMain {
            dependsOn(commonMain)

            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }

        jvmMain {
            dependsOn(commonMain)
            dependencies {
                api(compose.desktop.currentOs)
                implementation(libs.sqldelight.desktop.driver)
            }
        }

        val iosArm64Main by getting
        val iosX64Main by getting
        val iosSimulatorArm64Main by getting
        iosMain {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosX64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "info.javaway.spend_sense"
}

android {
    namespace = findProperty("app.namespace").toString()
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight{
    databases {
        create("AppDb"){
            packageName.set("info.javaway.spend_sense.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/db"))
        }
    }
}