package quevedo.soares.leandro.techtest.view.home

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import quevedo.soares.leandro.techtest.R

private const val ITEM_COUNT = 20
private val TEXTVIEW_MOCK_TEXT by lazy { (0..7).joinToString("") }

/**
 * A simple adapter that inflates a given layout and apply styling and animations to mimic a skeleton/placeholder loading effect
 *
 * @param layout The layout resource to be used
 **/
class SkeletonAdapter(@LayoutRes private val layout: Int) : RecyclerView.Adapter<SkeletonAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(this.layout, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = animateView(holder.itemView, position)

	override fun getItemCount() = ITEM_COUNT

	private fun animateView(view: View, offset: Int) {
		if (view is ViewGroup) {
			// Recursively calls itself for each child thus handling all views
			view.children.forEach { child -> animateView(child, offset) }
		} else {
			// Set the view's background to be light gray
			if (view.background != null) view.background.mutate().setTint(ContextCompat.getColor(view.context, R.color.divider_gray))
			else view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.divider_gray))

			// Treat textviews differently, attributing a transparent text to set their size
			if (view is TextView) {
				view.setTextColor(ContextCompat.getColor(view.context, android.R.color.transparent))
				view.text = TEXTVIEW_MOCK_TEXT
			}

			// Animate the view's alpha back and forth with offset creating a wave-sort effect
			AlphaAnimation(0.25f, 0.5f).apply {
				repeatCount = ValueAnimator.INFINITE
				repeatMode = ValueAnimator.REVERSE
				duration = 1000
				startOffset = 50L * offset
				interpolator = AccelerateDecelerateInterpolator()

				// Starts the animation binding it to the view
				// Therefore when the Adapter is collected by the GC, this animation will be too
				view.startAnimation(this)
			}
		}
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}