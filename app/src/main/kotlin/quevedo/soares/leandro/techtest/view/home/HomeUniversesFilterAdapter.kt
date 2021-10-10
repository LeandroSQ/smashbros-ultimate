package quevedo.soares.leandro.techtest.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.ItemUniverseBinding
import quevedo.soares.leandro.techtest.domain.model.Universe

typealias OnHomeUniversesItemSelected = (Universe) -> Unit

class HomeUniversesFilterAdapter(var items: List<Universe> = listOf()) : RecyclerView.Adapter<HomeUniversesFilterAdapter.ViewHolder>() {

	var onItemSelectedListener: OnHomeUniversesItemSelected? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemUniverseBinding.inflate(inflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = this.items[position]
		holder.bind(item)
	}

	override fun getItemCount() = this.items.size

	override fun getItemViewType(position: Int): Int {
		val item = this.items[position]
		return if (item.isSelected) 0 else 1
	}

	private fun onItemSelected(item: Universe, position: Int) {
		// Reset all currently selected items
		this.items.filter { it.isSelected }.forEach {
			it.isSelected = false
			notifyItemChanged(this.items.indexOf(it))
		}

		// Set the given item as the currently selected
		item.isSelected = true
		notifyItemChanged(position)

		// Invokes the listener
		this.onItemSelectedListener?.invoke(item)
	}

	inner class ViewHolder(private val binding: ItemUniverseBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Universe) {
			this.binding.apply {
				textViewText.text = item.name
				textViewText.setBackgroundResource(if (item.isSelected) R.color.darker_blue else R.color.blue)

				root.setOnClickListener { onItemSelected(item, adapterPosition) }
			}
		}

	}
}