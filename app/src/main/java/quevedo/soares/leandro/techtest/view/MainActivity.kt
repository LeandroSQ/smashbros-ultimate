package quevedo.soares.leandro.techtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.ActivityMainBinding
import quevedo.soares.leandro.techtest.helper.DataStoreHelper

class MainActivity : AppCompatActivity() {

	private val navController by lazy { findNavController(R.id.fragmentContainerView) }
	private lateinit var binding: ActivityMainBinding
	private val dataStore by lazy { DataStoreHelper(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Configure the splash screen
		this.setupSplashScreen()

		// Inflates the view
		this.binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Setups the navigation
		this.setupNavigation()
	}

	private fun setupSplashScreen() {
		installSplashScreen()
	}

	private fun setupNavigation() {
		// If the user already went trough the onboarding flow, direct him to the home screen, otherwise the onboarding flow
		val graph = if (dataStore.goneTroughOnBoarding) R.navigation.navigation_home else R.navigation.navigation_onboarding
		this.navController.setGraph(graph)
	}

}