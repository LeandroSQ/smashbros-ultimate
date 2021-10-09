package quevedo.soares.leandro.techtest.extension

import android.view.View

fun Boolean.toVisibility(): Int {
	return if (this) View.VISIBLE else View.GONE
}