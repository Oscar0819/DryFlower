import com.oscar0819.convention.implementation
import com.oscar0819.convention.ksp
import com.oscar0819.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                // Hilt
                implementation(libs.findLibrary("androidx.hilt.navigation.compose"))
                implementation(libs.findLibrary("dagger-hilt-android"))
                ksp(libs.findLibrary("dagger-hilt-compiler"))
            }
        }
    }
}