package quevedo.soares.leandro.techtest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {

	fun isConnected(context: Context): Boolean {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
			return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
		} else {
			for (network in connectivityManager.allNetworks) {
				val info = connectivityManager.getNetworkInfo(network)
				if (info != null && info.isConnected) return true
			}
		}

		return false
	}

}