package com.oscar0819.convention

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional

internal fun <T : Any> DependencyHandler.implementation(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("implementation", dependencyNotation.get())

internal fun <T : Any> DependencyHandler.debugImplementation(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("debugImplementation", dependencyNotation.get())

internal fun <T : Any> DependencyHandler.ksp(
    dependencyNotation: Optional<Provider<T>>
): Dependency? = add("ksp", dependencyNotation.get())

internal fun <T : Any> DependencyHandler.api(
    dependencyNotation: Provider<T>
): Dependency? = add("api", dependencyNotation)