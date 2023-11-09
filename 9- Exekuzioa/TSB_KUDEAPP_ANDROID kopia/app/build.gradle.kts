@file:Suppress("DEPRECATION")

import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.tsb_kudeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tsb_kudeapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    fun Packaging.() {
        exclude("META-INF/proguard/androidx-annotations.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    //noinspection GradleCompatible
    implementation("com.android.support:design:28.0.0")
    implementation("com.android.support:support-annotations:28.0.0")
    //noinspection GradleCompatible
    implementation("com.android.support:support-v4:28.0.0")
    implementation("androidx.annotation:annotation:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.android.material:material:1.3.0-alpha03")

    implementation ("org.postgresql:postgresql:42.2.5")
    implementation ("androidx.sqlite:sqlite:2.2.0")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

}

