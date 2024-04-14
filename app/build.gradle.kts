val koinVersion = "3.4.0"
val navVersion = "2.7.7"
val mockkVersion = "1.13.5"
val coreTestingVersion = "2.2.0"
val coroutineTestVersion = "1.7.3"
val extJunitVersion = "1.1.5"
val coreKtxVersion = "1.5.0"

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("io.realm.kotlin")
}

android {
    namespace = "com.osamaaftab.muzz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.osamaaftab.muzz"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.google.mlkit:smart-reply:17.0.3")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")

    //Koin dependencies
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    //Test dependencies
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("androidx.arch.core:core-testing:$coreTestingVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineTestVersion")
    androidTestImplementation("androidx.test.ext:junit:$extJunitVersion")
    androidTestImplementation("androidx.test:core-ktx:$coreKtxVersion")
    testImplementation("org.assertj:assertj-core:3.20.2")

}