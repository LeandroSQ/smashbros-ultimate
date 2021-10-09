package quevedo.soares.leandro.techtest.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingPage(
	@DrawableRes val image: Int,
	@StringRes val text: Int
)