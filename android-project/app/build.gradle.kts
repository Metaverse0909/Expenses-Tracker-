plugins {
    id("com.android.application")
}

android {
    namespace = "com.metaverse.expensetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.metaverse.expensetracker"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
}
