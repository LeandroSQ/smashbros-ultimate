package quevedo.soares.leandro.techtest.view.fighterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import coil.load
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.FragmentFighterDetailBinding
import quevedo.soares.leandro.techtest.extension.toFormattedInt

class FighterDetailFragment : Fragment() {

	private lateinit var binding: FragmentFighterDetailBinding
	private val navController by lazy { findNavController() }
	private val navArgs by navArgs<FighterDetailFragmentArgs>()
	private val fighter by lazy { this.navArgs.fighter }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		this.setupSharedElementTransition()

		this.binding = FragmentFighterDetailBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Postpone the enter transition, only starting it after the image has been loaded
		this.postponeEnterTransition()

		this.setupToolbar()
		this.setupView()
	}

	private fun setupSharedElementTransition() {
		sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_avatar)
		sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_avatar)
	}

	private fun setupToolbar() {
		this.binding.toolbar.setupWithNavController(this.navController)
	}

	private fun setupView() {
		this.binding.apply {
			// Maps the fighter information to the view
			this.textViewName.text = fighter.name
			this.textViewUniverse.text = fighter.universe
			this.textViewDownloads.text = getString(R.string.text_fighter_downloads, fighter.downloads.toFormattedInt())
			this.textViewPrice.text = getString(R.string.text_fighter_price, fighter.price)
			this.textViewDescription.text = fighter.description

			// Animates the rating bar after 250ms
			this.ratingBar.postDelayed({
				this.ratingBar.value = fighter.rate
			}, 250)

			// Uses coil to load the image with okhttp to handle caching
			this.imageViewAvatar.load(fighter.avatar) {
				// After the image has been loaded, start the transition
				listener(onSuccess = { _, _ ->
					startPostponedEnterTransition()
				})
			}
			this.imageViewAvatar.transitionName = fighter.avatar
		}
	}

}