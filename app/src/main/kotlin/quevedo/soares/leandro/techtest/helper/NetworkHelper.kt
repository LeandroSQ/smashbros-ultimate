package quevedo.soares.leandro.techtest.helper

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper(private val connectivityManager: ConnectivityManager) {

	/**
	 * Checks if the device is connected to the internet
	 *
	 * @return true when connected
	 **/
	fun isConnected(): Boolean {
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