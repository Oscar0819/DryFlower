plugins {
    alias(libs.plugins.dryflower.android.library)
}

android {
    namespace = "com.oscar0819.core.model"
}

dependencies {

    implementation(libs.kotlinx.serialization.json)

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}