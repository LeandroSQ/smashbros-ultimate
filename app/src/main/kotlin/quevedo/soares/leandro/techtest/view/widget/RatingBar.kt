package quevedo.soares.leandro.techtest.view.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.graphics.minus
import quevedo.soares.leandro.techtest.R
import quevedo.soares.leandro.techtest.extension.clamp
import kotlin.math.abs
import kotlin.math.ceil

// Constant to determine which events should be processed
private val CONSUMED_EVENTS = arrayOf(
	MotionEvent.ACTION_MOVE,
	MotionEvent.ACTION_UP,
	MotionEvent.ACTION_DOWN
)

typealias OnRateChangeListener = (Int) -> Unit

class RatingBar : View {

	var value = 0
		set(v) {
			if (isDragging) {
				field = v
				invalidate()
			} else {
				ValueAnimator.ofInt(field, v).apply {
					duration = 250
					interpolator = AccelerateDecelerateInterpolator()

					addUpdateListener {
						field = it.animatedValue as Int
						invalidate()
					}

					start()
				}
			}

		}

	var minValue = 0
	val starCount get() = this.maxValue - this.minValue
	var maxValue = 5

	var activeColor = Color.YELLOW
	var inactiveColor = Color.DKGRAY

	private lateinit var starImage: Drawable
	private var starSize = 0
	private var starPaddingHorizontal = 0f
	private var starPaddingVertical = 0f

	private var onChangeListener: OnRateChangeListener? = null

	private var initialPosition = PointF(0f, 0f)
	private var isDragging = false

	// region Constructors
	constructor(context: Context) : super(context) {
		setupAttributes(context)
	}

	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
		setupAttributes(context, attrs)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		setupAttributes(context, attrs, defStyleAttr)
	}

	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
		setupAttributes(context, attrs, defStyleAttr, defStyleRes)
	}
	// endregion

	init {
		// Enable the onDraw method
		setWillNotDraw(false)
	}

	private fun setupAttributes(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
		// Loads the star drawable
		this.starImage = ContextCompat.getDrawable(context, R.drawable.ic_star)!!

		// Ignore invalid attribute set
		if (attributeSet == null) return

		// Get the styled attributes of the view
		context.theme.obtainStyledAttributes(attributeSet, R.styleable.RatingBar, defStyleAttr, defStyleRes).apply {
			getInteger(R.styleable.RatingBar_android_value, 0).let { value = it }
			getInteger(R.styleable.RatingBar_android_min, 0).let { minValue = it }
			getInteger(R.styleable.RatingBar_android_max, 5).let { maxValue = it }
			getDimension(R.styleable.RatingBar_starPaddingHorizontal, 0f).let { starPaddingHorizontal = it }
			getDimension(R.styleable.RatingBar_starPaddingVertical, 0f).let { starPaddingVertical = it }
			getColor(R.styleable.RatingBar_colorActive, Color.YELLOW).let { activeColor = it }
			getColor(R.styleable.RatingBar_colorInactive, Color.DKGRAY).let { inactiveColor = it }

			// Disposes the attributes array
			recycle()
		}

		// Only for preview
		if (isInEditMode) {
			this.value = 3
		}
	}

	private fun processTouchEvent(horizontalPosition: Float) {
		// Calculate the progress of the slider
		val progress = horizontalPosition / this.width

		// Clamp it to the min and max values
		val newValue = ceil(progress * this.maxValue).toInt().clamp(this.minValue, this.maxValue)

		// Check if the value has changed
		if (newValue != value) {
			value = newValue

			this.invalidate()
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean {
		event?.let {
			// Ignore useless events
			if (!CONSUMED_EVENTS.contains(event.action)) return@let

			// Get the position into an pointF
			val position = PointF(event.x, event.y)

			when (event.action) {
				MotionEvent.ACTION_DOWN -> {
					// Stores the initial position of the first touch, to calculate the travel
					this.initialPosition = position

					return true
				}

				MotionEvent.ACTION_MOVE -> {
					// If it isn't dragging yet, check if the amount of horizontal travel was greater than the vertical travel distance
					if (!isDragging) {
						val distance = position - this.initialPosition

						// Discards vertical dragging
						if (abs(distance.x) <= abs(distance.y)) return false

						// Start horizontal dragging
						isDragging = true
					}

					// Disallow parent view (e.g scroll views and view pagers) to steal touch events from this view
					this.parent?.requestDisallowInterceptTouchEvent(true)

					this.processTouchEvent(position.x)

					return true
				}

				MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
					this.processTouchEvent(position.x)

					// Fires the onChangeListener
					this.onChangeListener?.invoke(this.value)

					// Stop dragging
					isDragging = false

					return true
				}
			}
		}

		return false
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)

		val widthMode = MeasureSpec.getMode(widthMeasureSpec)
		val widthSize = MeasureSpec.getSize(widthMeasureSpec)
		val heightSize = MeasureSpec.getSize(heightMeasureSpec).toFloat()

		// Calculate the minimum width
		this.starSize = kotlin.math.min(
			(widthSize - starPaddingHorizontal * (starCount)) / starCount,
			heightSize - starPaddingHorizontal * 2
		).toInt()
		val desiredWidth = ((starSize + starPaddingHorizontal) * starCount).toInt()

		when (widthMode) {
			MeasureSpec.AT_MOST -> {
				setMeasuredDimension(kotlin.math.min(desiredWidth, widthSize), (this.starSize + starPaddingVertical * 2).toInt())
			}

			MeasureSpec.EXACTLY -> {
				setMeasuredDimension(widthSize, (this.starSize + starPaddingVertical * 2).toInt())
			}

			MeasureSpec.UNSPECIFIED -> {
				setMeasuredDimension(desiredWidth, (this.starSize + starPaddingVertical * 2).toInt())
			}
		}

	}

	override fun onDraw(canvas: Canvas?) {
		// Ignore the first call
		if (canvas == null) return super.onDraw(canvas)

		// Calculate variables
		val width = this.width.toFloat()

		// Calculate the star size
		val maxWidth = starSize * starCount + starPaddingHorizontal * (starCount - 1)
		val margin = (width - maxWidth) / 2f

		// Saves the canvas matrix
		canvas.save()
		// Apply start-top padding
		canvas.translate(margin, starPaddingVertical)

		// Update the drawable sizing
		this.starImage.setBounds(0, 0, starSize, starSize)

		// Iterates trough the stars
		for (i in 0..starCount) {
			// Tints the star image accordingly
			this.starImage.setTint(if (i < this.value) activeColor else inactiveColor)

			// Draws the image in the canvas
			this.starImage.draw(canvas)

			// Translate the next star
			canvas.translate(starSize + starPaddingHorizontal, 0f)
		}

		// Restores the canvas matrix
		canvas.restore()
	}

}