/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.gradle.LibraryExtension
import com.oscar0819.convention.Const
import com.oscar0819.convention.implementation
import com.oscar0819.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "dryflower.android.library")
            apply(plugin = "dryflower.android.library.compose")
            apply(plugin = "dryflower.hilt")
//            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
//                configureGradleManagedDevices(this)
                defaultConfig.targetSdk = Const.targetSdk
            }

            dependencies {
                add("implementation", project(":designsystem"))
                add("implementation", project(":core:android"))
                add("implementation", project(":core:data"))
                implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose"))

//                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
//                "implementation"(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
//                "implementation"(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
//                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
//                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
//                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
                implementation(libs.findLibrary("kotlinx.serialization.json"))

//                "testImplementation"(libs.findLibrary("androidx.navigation.testing").get())
//                "androidTestImplementation"(
//                    libs.findLibrary("androidx.lifecycle.runtimeTesting").get(),
//                )
            }
        }
    }
}
