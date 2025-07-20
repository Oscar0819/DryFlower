import com.android.build.gradle.BasePlugin
import com.oscar0819.convention.configureAndroid
import com.oscar0819.convention.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            plugins.withType<BasePlugin>().configureEach {
                configureAndroid()
                configureKotlin()
            }
        }
    }
}