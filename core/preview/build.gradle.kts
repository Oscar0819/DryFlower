plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.android.library.compose)
    alias(libs.plugins.dryflower.hilt)
}

android {
    namespace = "com.oscar0819.core.preview"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
}