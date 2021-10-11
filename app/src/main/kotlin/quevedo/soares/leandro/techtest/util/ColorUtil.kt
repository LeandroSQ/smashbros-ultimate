package quevedo.soares.leandro.techtest.util

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import kotlin.math.max
import kotlin.math.min

object ColorUtil {

	@ColorInt
	fun getContrastColor(@ColorInt color: Int): Int {
		// Counting the perceptive luminance - human eye favors green color...
		val a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
		val d: Int = if (a < 0.5) {
			0 // bright colors - black font
		} else {
			255 // dark colors - white font
		}

		return Color.rgb(d, d, d)
	}

	fun lighten(@ColorInt color: Int, fraction: Double): Int {
		fun lightenChannel(color: Int, fraction: Double) = min(color + color * fraction, 255.0).toInt()

		val red = lightenChannel(Color.red(color), fraction)
		val green = lightenChannel(Color.green(color), fraction)
		val blue = lightenChannel(Color.blue(color), fraction)

		val alpha = Color.alpha(color)
		return Color.argb(alpha, red, green, blue)
	}

	fun darken(@ColorInt color: Int, fraction: Double): Int {
		fun darkenChannel(color: Int, fraction: Double) = max(color - color * fraction, 0.0).toInt()

		val red = darkenChannel(Color.red(color), fraction)
		val green = darkenChannel(Color.green(color), fraction)
		val blue = darkenChannel(Color.blue(color), fraction)


		val alpha = Color.alpha(color)
		return Color.argb(alpha, red, green, blue)
	}

	fun generateGradient(@ColorInt color: Int, shape: Int): GradientDrawable {
		val colors = intArrayOf(
			lighten(color, 0.45),
			color,
			darken(color, 0.35)
		)

		return GradientDrawable(GradientDrawable.Orientation.TL_BR, colors).apply {
			this.shape = shape
		}
	}

	@ColorInt
	fun getBackgroundColor(drawable: Drawable, @ColorInt default: Int): Int {
		val palette = getPalette(drawable)
		return palette.getDarkVibrantColor(palette.getDominantColor(palette.getDarkMutedColor(palette.getLightVibrantColor(default))))
	}

	private fun getPalette(drawable: Drawable): Palette {
		val bitmap = drawable.toBitmap()
		return Palette.from(bitmap).generate()
	}


}