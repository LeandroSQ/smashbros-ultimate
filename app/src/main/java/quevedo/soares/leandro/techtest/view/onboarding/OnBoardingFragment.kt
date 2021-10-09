package quevedo.soares.leandro.techtest.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.FragmentOnboardingBinding
import quevedo.soares.leandro.techtest.extension.doOnPageChange
import quevedo.soares.leandro.techtest.extension.setupWithIndicator
import quevedo.soares.leandro.techtest.helper.DataStoreHelper

class OnBoardingFragment: Fragment() {

	private val viewModel: OnBoardingViewModel by viewModel()
	private lateinit var binding: FragmentOnboardingBinding
	private val navController by lazy { findNavController() }
	private val dataStore by lazy { DataStoreHelper(requireContext()) }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		this.binding = FragmentOnboardingBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		this.setupViewPager()
		this.setupFAB()
	}

	private fun setupViewPager() {
		this.binding.apply {
			viewPager.adapter = OnBoardingPageAdapter(viewModel.pages)
			viewPager.setupWithIndicator(viewPagerIndicator)
			viewPager.doOnPageChange(this@OnBoardingFragment::onPageChange)
		}
	}

	private fun setupFAB() {
		this.binding.fabContinue.setOnClickListener(this::onFabClick)
	}

	// region Event listeners
	private fun onPageChange(position: Int) {
		// Determine whether the FAB should be visible
		val isFabVisible = position >= this.viewModel.pages.size - 1

		this.binding.fabContinue.apply {
			post {
				if (isFabVisible) show()
				else hide()
			}
		}
	}

	private fun onFabClick(fab: View) {
		// Disables the button, thus ignoring double-clicks
		fab.isEnabled = false

		// Stores that the user already went trough the onboarding flow
		this.dataStore.goneTroughOnBoarding = true

		// Navigate to the home screen
		this.navController.navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToNavigationMain())
	}
	// endregion


}