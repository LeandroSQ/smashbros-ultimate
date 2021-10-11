package quevedo.soares.leandro.techtest.view.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.FragmentHomeBinding
import quevedo.soares.leandro.techtest.databinding.ItemFighterBinding
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.Universe

class HomeFragment : Fragment() {

	private val viewModel: HomeViewModel by sharedViewModel()
	private val navController by lazy { findNavController() }
	private var binding: FragmentHomeBinding? = null

	private val fightersAdapter by lazy { HomeFighterAdapter() }
	private var universes = listOf<Universe>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		lifecycleScope.launchWhenCreated {
			viewModel.getFighters(allowCache = true)
			viewModel.getUniverses(allowCache = true)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		this.binding = FragmentHomeBinding.inflate(inflater, container, false)
		return this.binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Postpone the enter transition, only starting it after the recyclerview has been configured
		this.postponeEnterTransition()

		setupToolbar()
		setupSwipeToRefreshLayout()
		setupTabLayout()
		setupFightersRecyclerView()
	}

	private fun setupToolbar() {
		this.binding?.toolbar?.apply {
			setupWithNavController(navController, AppBarConfiguration(setOf(R.id.homeFragment, R.id.onBoardingFragment)))
			setOnMenuItemClickListener {
				onFilterButtonClick()
				true
			}
		}

	}

	private fun setupTabLayout() {
		if (universes.isEmpty()) return

		this.binding?.tabLayout?.apply {

			// Clears the current tabs
			removeAllTabs()

			// Adds the 'All' tab
			addTab(newTab().setText("All"))

			// Maps every universe into a tab
			universes.forEach { addTab(newTab().setText(it.name)) }

			addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

				override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

				override fun onTabSelected(tab: TabLayout.Tab?) {
					onUniverseSelected(tab.takeIf { selectedTabPosition != 0 }?.text?.toString())
				}

				override fun onTabReselected(tab: TabLayout.Tab?) {
					// When re-select a tab, select 'All'
					post { selectTab(getTabAt(0)) }
				}

			})

			visibility = View.VISIBLE
		}
	}

	private fun setupSwipeToRefreshLayout() {
		binding?.swipeToRefreshLayout?.setOnRefreshListener {
			binding?.swipeToRefreshLayout?.isRefreshing = false

			// Load data directly from the API, skipping cache
			viewModel.getFighters(allowCache = false)
		}
	}

	private fun setupObservers() {
		lifecycleScope.launchWhenCreated {
			viewModel.viewState.collect { state ->
				when (state) {

					// region Fighters
					HomeViewModel.ViewState.LoadingFighters -> {
						// Hide the no result image
						binding?.imageViewNoResults?.visibility = View.GONE

						// Shows the skeleton loading
						binding?.recyclerViewFighters?.adapter = SkeletonAdapter(R.layout.item_fighter)
					}

					is HomeViewModel.ViewState.FightersLoaded -> {
						// Update the recycler view
						fightersAdapter.setItems(state.list)
						binding?.recyclerViewFighters?.adapter = fightersAdapter

						// Shows the no result image if the list is empty
						if (state.list.isEmpty()) binding?.imageViewNoResults?.visibility = View.VISIBLE
					}

					is HomeViewModel.ViewState.FightersError -> {
						Log.e("HomeFragment", "Error -> ${state.throwable}")
						binding?.root?.let { Snackbar.make(it, state.throwable.message ?: getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show() }
					}
					// endregion

					// region Universes
					HomeViewModel.ViewState.LoadingUniverses -> {
						binding?.tabLayout?.visibility = View.GONE
					}

					is HomeViewModel.ViewState.UniversesLoaded -> {
						universes = state.list
						setupTabLayout()
					}

					is HomeViewModel.ViewState.UniversesError -> {
						Log.e("HomeFragment", "Error -> ${state.throwable}")
						binding?.root?.let { Snackbar.make(it, state.throwable.message ?: getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show() }
					}
					// endregion

				}
			}
		}
	}

	private fun setupFightersRecyclerView() {
		this.binding?.recyclerViewFighters?.apply {
			// Bind the adapter's on item click listener
			fightersAdapter.onItemSelectedListener = this@HomeFragment::onFighterSelected
			// Sets the recycler view's adapter
			adapter = fightersAdapter

			// When about to draw the recyclerview, start the postponed transition
			viewTreeObserver.addOnPreDrawListener {
				startPostponedEnterTransition()
				true
			}
		}
	}

	private fun onFilterButtonClick() {
		this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToFighterFilterFragment())
	}

	private fun onUniverseSelected(universeName: String? = null) {
		// Update the filter
		this.viewModel.filter.universeName = universeName
		this.viewModel.getFighters()
	}

	private fun onFighterSelected(item: Fighter, binding: ItemFighterBinding) {
		// Setup the views to be shared in the transition
		val extras = FragmentNavigatorExtras(binding.imageViewAvatar to binding.imageViewAvatar.transitionName)
		// Navigate to the fighter detail fragment
		this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToFighterDetailFragment(item), extras)
	}

}