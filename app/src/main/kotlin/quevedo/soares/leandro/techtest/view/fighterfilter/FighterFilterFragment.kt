package quevedo.soares.leandro.techtest.view.fighterfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import quevedo.soares.leandro.techtest.databinding.FragmentFighterFilterBinding
import quevedo.soares.leandro.techtest.domain.enumerator.SortByPropertyEnum
import quevedo.soares.leandro.techtest.view.home.HomeViewModel

class FighterFilterFragment : Fragment() {

	private val viewModel by sharedViewModel<HomeViewModel>()
	private lateinit var binding: FragmentFighterFilterBinding
	private val navController by lazy { findNavController() }

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		this.binding = FragmentFighterFilterBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		this.setupApplyButton()
		this.setupResetButton()
		this.setupRadioGroup()
		this.setupRatingBar()
		this.setupToolbar()
	}

	private fun setupToolbar() {
		this.binding.toolbar.setupWithNavController(this.navController)
	}

	private fun setupResetButton() {
		this.binding.apply {
			buttonReset.setOnClickListener {
				// Clears the rating
				radioGroup.clearCheck()
				viewModel.filter.rating = null

				// Clears the sorting
				ratingBar.value = 0
				viewModel.filter.sortBy = null
			}
		}
	}

	private fun setupApplyButton() {
		this.binding.apply {
			buttonApply.setOnClickListener {
				// Search for the selected radio button
				(radioGroup.children.find { it.id == radioGroup.checkedRadioButtonId } as? RadioButton)?.let { button ->
					viewModel.filter.sortBy = SortByPropertyEnum.valueOf(button.text.toString())
				} ?: run {
					viewModel.filter.sortBy = null
				}

				// Apply the filtering by rate
				viewModel.filter.rating = if (ratingBar.value == 0) null else ratingBar.value

				viewModel.getFighters(true)

				// Navigates back
				navController.popBackStack()
			}
		}
	}

	private fun setupRatingBar() {

	}

	private fun setupRadioGroup() {

	}


}