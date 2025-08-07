plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.android.library.compose)
    alias(libs.plugins.dryflower.hilt)
}

android {
    namespace = "com.oscar0819.designsystem"
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}