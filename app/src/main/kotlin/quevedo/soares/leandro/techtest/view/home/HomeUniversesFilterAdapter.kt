package quevedo.soares.leandro.techtest.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.ItemUniverseBinding
import quevedo.soares.leandro.techtest.domain.model.Universe

typealias OnHomeUniversesItemSelected = (Universe?) -> Unit

class HomeUniversesFilterAdapter : RecyclerView.Adapter<HomeUniversesFilterAdapter.ViewHolder>() {

	private var items: List<Universe> = listOf()

	var onItemSelectedListener: OnHomeUniversesItemSelected? = null

	var selectedItem: Universe? = null
		private set

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemUniverseBinding.inflate(inflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = this.getItem(position)
		holder.bind(item)
	}

	private fun getItem(position: Int) = if (position == 0) null else this.items[position - 1]

	// Appends a virtual item, the 'All' button
	override fun getItemCount() = this.items.size + 1

	private fun onItemSelected(item: Universe?, position: Int) {
		// Notify that the current item changed
		val lastSelectedIndex = items.indexOf(selectedItem) + 1

		// If clicked again on the selected item, de-select it
		if (selectedItem == item) {
			selectedItem = null// Null meaning 'All'
			notifyItemChanged(0)
		} else {
			// Sets the new current item
			selectedItem = item
			notifyItemChanged(position)
		}

		// Notify changes
		notifyItemChanged(lastSelectedIndex)

		// Calls the listener
		onItemSelectedListener?.invoke(selectedItem)
	}

	/**
	 * Updates the list to a new set of items
	 * Resets the [selectedItem] as well
	 **/
	@SuppressLint("NotifyDataSetChanged")
	fun setItems(items: List<Universe>) {
		this.items = items

		// Update the selected item
		if (this.selectedItem != null) this.selectedItem = this.items.find { it.objectId == selectedItem?.objectId }

		this.notifyDataSetChanged()
	}

	inner class ViewHolder(private val binding: ItemUniverseBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Universe?) {
			this.binding.apply {
				textViewText.text = item?.name ?: textViewText.context.getString(R.string.text_all)

				// Sets the background according to the item state
				textViewText.setBackgroundResource(if (item == selectedItem) R.drawable.shape_universe_background_selected else R.drawable.shape_universe_background)

				// Sets the whole view on click listener
				root.setOnClickListener { onItemSelected(item, adapterPosition) }
			}
		}

	}
}