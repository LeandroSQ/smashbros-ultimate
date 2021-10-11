package quevedo.soares.leandro.techtest.view.home

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
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
	private lateinit var binding: FragmentHomeBinding

	private val fightersAdapter by lazy { HomeFighterAdapter() }
	private val universesAdapter by lazy { HomeUniversesFilterAdapter() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setupObservers()

		lifecycleScope.launchWhenCreated {
			viewModel.getFighters(allowCache = true)
			viewModel.getUniverses(allowCache = true)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		this.binding = FragmentHomeBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Postpone the enter transition, only starting it after the recyclerview has been configured
		this.postponeEnterTransition()

		setupToolbar()
		setupSwipeToRefreshLayout()
		setupUniversesRecyclerView()
		setupFightersRecyclerView()
	}

	private fun setupToolbar() {
		this.binding.toolbar.setupWithNavController(this.navController, AppBarConfiguration(setOf(R.id.homeFragment, R.id.onBoardingFragment)))
		this.binding.toolbar.setOnMenuItemClickListener {
			onFilterButtonClick()
			true
		}
	}

	private fun setupSwipeToRefreshLayout() {
		binding.swipeToRefreshLayout.setOnRefreshListener {
			binding.swipeToRefreshLayout.isRefreshing = false

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
						binding.imageViewNoResults.visibility = View.GONE

						// Shows the skeleton loading
						binding.recyclerViewFighters.adapter = SkeletonAdapter(R.layout.item_fighter)
					}

					is HomeViewModel.ViewState.FightersLoaded -> {
						// Update the recycler view
						fightersAdapter.setItems(state.list)
						binding.recyclerViewFighters.adapter = fightersAdapter

						// Shows the no result image if the list is empty
						if (state.list.isEmpty()) binding.imageViewNoResults.visibility = View.VISIBLE
					}

					is HomeViewModel.ViewState.FightersError -> {
						Log.e("HomeFragment", "Error -> ${state.throwable}")
						Snackbar.make(binding.root, state.throwable.message ?: getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show()
					}
					// endregion

					// region Universes
					HomeViewModel.ViewState.LoadingUniverses -> {
						// Shows the skeleton loading
						binding.recyclerViewUniversesFilter.adapter = SkeletonAdapter(R.layout.item_universe)
					}

					is HomeViewModel.ViewState.UniversesLoaded -> {
						// Update the recycler view
						universesAdapter.setItems(state.list)
						binding.recyclerViewUniversesFilter.adapter = universesAdapter
					}

					is HomeViewModel.ViewState.UniversesError -> {
						Log.e("HomeFragment", "Error -> ${state.throwable}")
						Snackbar.make(binding.root, state.throwable.message ?: getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show()
					}
					// endregion

				}
			}
		}
	}

	private fun setupUniversesRecyclerView() {
		this.binding.recyclerViewUniversesFilter.apply {
			// Bind the adapter's on item click listener
			universesAdapter.onItemSelectedListener = this@HomeFragment::onUniverseSelected
			// Sets the recycler view's adapter
			adapter = universesAdapter
		}
	}

	private fun setupFightersRecyclerView() {
		this.binding.recyclerViewFighters.apply {
			// Bind the adapter's on item click listener
			fightersAdapter.onItemSelectedListener = this@HomeFragment::onFighterSelected
			// Sets the recycler view's adapter
			adapter = fightersAdapter

			// Embeds a item divider as decorator
			addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
				// Set the decorator color
				setDrawable(ColorDrawable(ContextCompat.getColor(requireContext(), R.color.divider_gray)))
			})

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

	private fun onUniverseSelected(item: Universe?) {
		// Update the filter
		this.viewModel.filter.universeName = item?.name
		this.viewModel.getFighters()
	}

	private fun onFighterSelected(item: Fighter, binding: ItemFighterBinding) {
		// Setup the views to be shared in the transition
		val extras = FragmentNavigatorExtras(binding.imageViewAvatar to binding.imageViewAvatar.transitionName)
		// Navigate to the fighter detail fragment
		this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToFighterDetailFragment(item), extras)
	}

}