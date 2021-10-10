package quevedo.soares.leandro.techtest.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.koin.java.KoinJavaComponent
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.databinding.ItemFighterBinding
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.extension.toFormattedInt

typealias OnHomeFighterItemSelected = (Fighter) -> Unit

class HomeFighterAdapter : ListAdapter<Fighter, HomeFighterAdapter.ViewHolder>(this) {

	var onItemSelectedListener: OnHomeFighterItemSelected? = null

	init {
		submitList(listOf())
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemFighterBinding.inflate(inflater, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = this.getItem(position)
		holder.bind(item)
	}

	inner class ViewHolder(private val binding: ItemFighterBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Fighter) {
			this.binding.apply {
				val res = root.context.resources

				// Uses coil to load the image with okhttp to handle caching
				imageViewAvatar.load(item.avatar, imageLoader = KoinJavaComponent.getKoin().get())

				textViewName.text = item.name
				textViewUniverse.text = item.universe
				textViewPrice.text = res.getString(R.string.text_fighter_price, item.price)
				textViewRate.text = res.getString(R.string.text_fighter_rate, item.rate)
				textViewDownloads.text = res.getString(R.string.text_fighter_downloads, item.downloads.toFormattedInt())

				// Set the whole layout to be clickable
				root.setOnClickListener { onItemSelectedListener?.invoke(item) }
			}
		}

	}

	private companion object : DiffUtil.ItemCallback<Fighter>() {

		override fun areItemsTheSame(oldItem: Fighter, newItem: Fighter) = oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: Fighter, newItem: Fighter) = oldItem.isContentEqualTo(newItem)

	}


}