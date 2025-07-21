plugins {
    alias(libs.plugins.dryflower.feature)
    alias(libs.plugins.dryflower.android.library.compose)
}

android {
    namespace = "com.oscar0819.feature.search"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}