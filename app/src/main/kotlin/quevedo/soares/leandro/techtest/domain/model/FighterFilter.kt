package quevedo.soares.leandro.techtest.domain.model

import quevedo.soares.leandro.techtest.domain.enumerator.SortByPropertyEnum

data class FighterFilter(
	var sortBy: SortByPropertyEnum? = null,
	var rating: Int? = null,
	var universeName: String? = null
)