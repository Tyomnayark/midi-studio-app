plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}
val debug = "debug"
val release = "release"

android {

    namespace = "com.tyom.notestudio"
    compileSdk = 34

    signingConfigs {
        register("for_release") {
            keyAlias = project.findProperty("KEY_ALIAS") as String
            keyPassword = project.findProperty("KEY_PASSWORD") as String
            storeFile = file(project.findProperty("STORE_FILE") as String)
            storePassword = project.findProperty("STORE_PASSWORD") as String
        }
    }

    defaultConfig {
        applicationId = "com.tyom.notestudio"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    buildTypes {
        release {
//            isMinifyEnabled = false
//            postprocessing.isRemoveUnusedCode = false
//            postprocessing.isRemoveUnusedResources = false
//            postprocessing.isObfuscate = false
//            postprocessing.isOptimizeCode = true
            proguardFiles("proguard-rules.pro")
            buildConfigField("String", "TYPE", "\"${release}\"")
            signingConfig = signingConfigs.getByName("for_release")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isJniDebuggable = true
            isDebuggable = true
            proguardFiles("proguard-rules.pro")
            buildConfigField("String", "TYPE", "\"${debug}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/gradle/incremental.annotation.processors"
            )
        }
    }

    applicationVariants.all {
        val variant = this
        when (variant.baseName) {
            "$debug" -> variant.resValue("string", "app_name", "debug $versionName")
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core_utils"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":feature_main"))

    kapt(libs.dagger.hilt.compiler)
    implementation(libs.dagger.hilt)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(libs.androidx.ui.test.junit4)
}