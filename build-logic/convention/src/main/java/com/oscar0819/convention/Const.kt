package com.oscar0819.convention

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Const {
    const val compileSdk = 36
    const val minSdk = 26
    const val targetSdk = 36
    // TODO Upgrade
    val JAVA_VERSION = JavaVersion.VERSION_17
    val JVM_TARGET = JvmTarget.JVM_17
}