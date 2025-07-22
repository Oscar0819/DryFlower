
plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.hilt)
}

android {
    namespace = "com.oscar0819.core.network"

    defaultConfig {
        buildConfigField(
            "String",
            "ITUNES_URL",
            "\"https://itunes.apple.com/\""
        )
    }

}

dependencies {
    api(projects.core.model)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}