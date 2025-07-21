plugins {
    alias(libs.plugins.dryflower.feature)
}

android {
    namespace = "com.oscar0819.feature.main"
}

dependencies {
    implementation(projects.designsystem)
    implementation(projects.core.android)
    implementation(projects.core.data)

    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}