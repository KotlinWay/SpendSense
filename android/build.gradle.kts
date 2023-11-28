plugins{
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android{
    namespace = findProperty("app.namespace").toString()
    compileSdk = findProperty("android.compileSdk").toString().toInt()

    defaultConfig{
        minSdk = findProperty("android.minSdk").toString().toInt()
        targetSdk = findProperty("android.targetSdk").toString().toInt()
        applicationId = "info.javaway.spend_sense"
        versionCode = 1
        versionName = "0.1"
    }

    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures{
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    dependencies{
        implementation(libs.androidx.activity.compose)
        implementation(project(":shared"))
    }
}