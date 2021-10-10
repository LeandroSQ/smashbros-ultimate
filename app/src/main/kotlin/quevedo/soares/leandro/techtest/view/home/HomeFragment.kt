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
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.FragmentHomeBinding
import quevedo.soares.leandro.techtest.databinding.ItemFighterBinding
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.Universe

class HomeFragment : Fragment() {

	private val viewModel: HomeViewModel by viewModel()
	private val navController by lazy { findNavController() }
	private lateinit var binding: FragmentHomeBinding

	private val fightersAdapter by lazy { HomeFighterAdapter() }
	private val universesAdapter by lazy { HomeUniversesFilterAdapter() }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		this.binding = FragmentHomeBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Postpone the enter transition, only starting it after the recyclerview has been configured
		this.postponeEnterTransition()

		this.setupToolbar()
		this.setupSwipeToRefreshLayout()

		lifecycleScope.launchWhenCreated { setupObservers() }
		lifecycleScope.launchWhenCreated { loadData(allowCache = true) }

		this.setupUniversesRecyclerView()
		this.setupFightersRecyclerView()
	}

	private fun loadData(allowCache: Boolean) {
		viewModel.getFighters(allowCache = allowCache)
		viewModel.getUniverses(allowCache)
	}

	private fun setupToolbar() {
		this.binding.toolbar.setupWithNavController(this.navController)

	}

	private fun setupSwipeToRefreshLayout() {
		binding.swipeToRefreshLayout.setOnRefreshListener {
			binding.swipeToRefreshLayout.isRefreshing = false

			// Load data directly from the API, skipping cache
			loadData(allowCache = false)
		}
	}

	private suspend fun setupObservers() {
		viewModel.viewState.collect { state ->
			when (state) {

				// region Fighters
				HomeViewModel.ViewState.LoadingFighters -> {

				}

				is HomeViewModel.ViewState.FightersLoaded -> {
					fightersAdapter.submitList(state.list)
					fightersAdapter.notifyDataSetChanged()
				}

				is HomeViewModel.ViewState.FightersError -> {
					Log.e("HomeFragment", "Error -> ${state.throwable}")
				}
				// endregion

				// region Universes
				HomeViewModel.ViewState.LoadingUniverses -> {

				}

				is HomeViewModel.ViewState.UniversesLoaded -> {
					universesAdapter.setItems(state.list)
				}

				is HomeViewModel.ViewState.UniversesError -> {
					Log.e("HomeFragment", "Error -> ${state.throwable}")
				}
				// endregion

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

	private fun onFilterButtonClick(button: View) {

	}

	private fun onUniverseSelected(item: Universe?) {
		this.viewModel.getFighters(fromUniverse = item)
	}

	private fun onFighterSelected(item: Fighter, binding: ItemFighterBinding) {
		// Setup the views to be shared in the transition
		val extras = FragmentNavigatorExtras(binding.imageViewAvatar to binding.imageViewAvatar.transitionName)
		// Navigate to the fighter detail fragment
		this.navController.navigate(HomeFragmentDirections.actionHomeFragmentToFighterDetailFragment(item), extras)
	}

}