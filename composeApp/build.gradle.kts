import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            //
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.activity)
            implementation(libs.androidx.fragment)
            //
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            //
            implementation(libs.ktor.client.cio)
            //
            //implementation(libs.mlkit.text.recognition) // For ML Kit text recognition (uses Google Play Services)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            // JSON parsing
            implementation(libs.kotlinx.serialization)
            // Ktor client
            implementation(libs.ktor.client.core)
            // Supabase
            //implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.postgrest.kt)
            implementation(libs.supabase.auth.kt)
            //implementation(libs.supabase.realtime.kt)
            //implementation(libs.supabase.storage.kt)
            //implementation(libs.supabase.functions.kt)
            //implementation(libs.supabase.compose.auth)
            //implementation(libs.supabase.compose.auth.ui)
            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            //
            implementation(libs.ktor.client.cio)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
    /*
    /Users/frankbouwens/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-wasm-js/2.1.0/b96271506fb37f4d8d1c63db7a57384ab16ae218/kotlin-stdlib-wasm-js-2.1.0.klib,
    /Users/frankbouwens/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib-wasm/1.9.0/ec6525e2b8f7cf4f4fe62db24012ec26d0803ea/kotlin-stdlib-wasm-1.9.0.klib
     */
}

android {
    namespace = "nl.frankkie.poketcghelper"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "nl.frankkie.poketcghelper"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.foundation.android)
    implementation("io.ktor:ktor-client-logging:3.0.2")
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "nl.frankkie.poketcghelper.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "nl.frankkie.poketcghelper"
            packageVersion = "1.0.0"
        }
    }
}
