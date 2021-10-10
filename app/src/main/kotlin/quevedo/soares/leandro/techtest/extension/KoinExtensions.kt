package quevedo.soares.leandro.techtest.extension

import org.koin.core.module.Module
import quevedo.soares.leandro.techtest.util.isInDebugMode

/**
 * Declares a single instance, only if in a debug variant
 *
 * @see [isInDebugMode]
 **/
inline fun <reified T> Module.singleDebug(crossinline declaration: () -> T) {
	if (isInDebugMode()) this.single { declaration.invoke() }
}