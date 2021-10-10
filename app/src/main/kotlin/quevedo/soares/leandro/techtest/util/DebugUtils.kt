package quevedo.soares.leandro.techtest.util

import quevedo.soares.leandro.techtest.BuildConfig

fun isInDebugMode() = "debug" in BuildConfig.BUILD_TYPE.lowercase()

fun isInReleaseMode() = "release" in BuildConfig.BUILD_TYPE.lowercase()
