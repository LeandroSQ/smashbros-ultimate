package quevedo.soares.leandro.techtest.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import quevedo.soares.leandro.techtest.databinding.FragmentHomeBinding
import quevedo.soares.leandro.techtest.domain.model.Fighter

class HomeFragment : Fragment() {

	private val viewModel: HomeViewModel by viewModel()
	private val navController by lazy { findNavController() }
	private lateinit var binding: FragmentHomeBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		this.binding = FragmentHomeBinding.inflate(inflater, container, false)
		return this.binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		this.setupToolbar()
	}

	private fun setupToolbar()  {
		this.binding.toolbar.setupWithNavController(this.navController)
	}

	private fun setupRecyclerView() {

	}

	private fun onFilterButtonClick(button: View) {

	}

	private fun onItemSelected(item: Fighter) {

	}

}