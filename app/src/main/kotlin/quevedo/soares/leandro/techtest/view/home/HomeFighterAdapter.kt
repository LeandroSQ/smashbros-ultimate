package quevedo.soares.leandro.techtest.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import org.koin.java.KoinJavaComponent
import quevedo.soares.leandro.techtest.databinding.ItemFighterBinding
import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.util.ColorUtil


typealias OnHomeFighterItemSelected = (Fighter, ItemFighterBinding) -> Unit

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

	@SuppressLint("NotifyDataSetChanged")
	fun setItems(list: List<Fighter>) {
		this.submitList(list)
		this.notifyDataSetChanged()
	}

	inner class ViewHolder(private val binding: ItemFighterBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Fighter) {
			this.binding.apply {
				val res = root.context.resources

				// Uses coil to load the image with okhttp to handle caching
				val imageLoader = KoinJavaComponent.getKoin().get<ImageLoader>()
				imageLoader.enqueue(
					ImageRequest.Builder(root.context)
							.data(item.avatar)
							.target { result ->
								imageViewAvatar.setImageDrawable(result)
								imageViewAvatar.background = ColorUtil.generateGradient(ColorUtil.getBackgroundColor(result, Color.BLACK), GradientDrawable.OVAL)
							}
							.build()
				)

				imageViewAvatar.transitionName = item.avatar

				textViewName.text = item.name

				// Set the whole layout to be clickable
				root.setOnClickListener { onItemSelectedListener?.invoke(item, this) }
			}
		}

	}

	private companion object : DiffUtil.ItemCallback<Fighter>() {

		override fun areItemsTheSame(oldItem: Fighter, newItem: Fighter) = oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: Fighter, newItem: Fighter) = oldItem.isContentEqualTo(newItem)

	}


}