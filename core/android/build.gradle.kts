plugins {
    alias(libs.plugins.dryflower.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.oscar0819.core.android"
}

dependencies {

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}