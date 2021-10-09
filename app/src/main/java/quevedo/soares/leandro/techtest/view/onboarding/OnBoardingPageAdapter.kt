package quevedo.soares.leandro.techtest.view.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import quevedo.soares.leandro.techtest.databinding.ItemOnboardingPageBinding
import quevedo.soares.leandro.techtest.domain.model.OnBoardingPage

class OnBoardingPageAdapter(private var items: List<OnBoardingPage>) : RecyclerView.Adapter<OnBoardingPageAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		// Fetch the layout inflater from the parent view context
		val inflater = LayoutInflater.from(parent.context)
		// Inflate the item and creates the view holder
		val binding = ItemOnboardingPageBinding.inflate(inflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = this.items[position]
		holder.bind(item)
	}

	override fun getItemCount() = this.items.size

	class ViewHolder(private val binding: ItemOnboardingPageBinding): RecyclerView.ViewHolder(binding.root) {

		fun bind(item: OnBoardingPage) {
			// Bind the item's resources to the view
			this.binding.apply {
				imageView.setImageResource(item.image)
				textView.setText(item.text)
			}
		}

	}

}