plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.dryflower.hilt)
}

android {
    namespace = "com.oscar0819.core.domain"
}

dependencies {
    api(projects.core.data)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}