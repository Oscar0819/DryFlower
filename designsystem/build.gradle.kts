plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.android.library.compose)
}

android {
    namespace = "com.oscar0819.designsystem"
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}