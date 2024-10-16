import build.Build
import build.BuildConfig
import build.BuildDimensions
import build.BuildTypes
import com.android.build.gradle.ProguardFiles
import falvors.BuildFlavor
import plugs.BuildPlugins
import release.ReleaseConfig
import test.TestBuildConfig
import test.TestDependencies

plugins {
    id(plugs.BuildPlugins.ANDROID_APPLICATION)
    id(plugs.BuildPlugins.KOTLIN_ANDROID)
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk =BuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk =BuildConfig.MIN_SDK_VERSION
        targetSdk = BuildConfig.TARGET_SDK_VERSION
        versionCode = ReleaseConfig.VERSION_CODE
        versionName = ReleaseConfig.VERSION_NAME

        testInstrumentationRunner =TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
      getByName(BuildTypes.RELEASE) {
          proguardFiles(
              getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
          )
          isMinifyEnabled = Build.Release.isMinifyEnabled
          enableUnitTestCoverage =Build.Release.enableUnitTestCoverage
          isDebuggable =Build.Release.isDebuggable
      }
        getByName(BuildTypes.DEBUG){
            isMinifyEnabled = Build.Debug.isMinifyEnabled
            enableUnitTestCoverage =Build.Debug.enableUnitTestCoverage
            isDebuggable =Build.Debug.isDebuggable
            versionNameSuffix = Build.Debug.versionNameSuffix
            applicationIdSuffix = Build.Debug.applicationIdSuffix
        }
        create(BuildTypes.RELEASE_EXTERNAL_QA){
            isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
            enableUnitTestCoverage =Build.ReleaseExternalQa.enableUnitTestCoverage
            isDebuggable =Build.ReleaseExternalQa.isDebuggable
            versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
            applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
        }
    }
    flavorDimensions.add(BuildDimensions.APP)
    flavorDimensions.add(BuildDimensions.STORE)
    productFlavors{
        BuildFlavor.Google.create(this)
        BuildFlavor.Huawei.create(this)
        BuildFlavor.Driver.create(this)
        BuildFlavor.Client.create(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(deps.Dependencies.ANDROIDX_CORE)
    implementation(deps.Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(deps.Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(deps.Dependencies.ANDROIDX_UI)
    implementation(deps.Dependencies.ANDROIDX_UI_GRAPHICS)
    implementation(deps.Dependencies.ANDROIDX_UI_TOOLING_PREVIEW)
    implementation(deps.Dependencies.ANDROIDX_MATERIAL3)


    testImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST)

    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST_MANIFEST)
}