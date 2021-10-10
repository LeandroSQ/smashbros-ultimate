package quevedo.soares.leandro.techtest.domain.model

import quevedo.soares.leandro.techtest.domain.enum.SortByPropertyEnum

data class FighterFilter(
	val sortBy: SortByPropertyEnum,
	val rating: Int
)