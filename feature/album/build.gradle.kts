plugins {
    alias(libs.plugins.dryflower.feature)
}

android {
    namespace = "com.oscar0819.feature.album"
}

dependencies {
    implementation(libs.coil.kt.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}