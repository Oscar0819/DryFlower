plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.hilt)
}

android {
    namespace = "com.oscar0819.core.data"
}

dependencies {
    api(projects.core.network)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}