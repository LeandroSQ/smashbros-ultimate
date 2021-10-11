package quevedo.soares.leandro.techtest.util

import quevedo.soares.leandro.techtest.domain.model.Fighter
import quevedo.soares.leandro.techtest.domain.model.Universe
import java.util.*

object DataMock {

	val universes = listOf(
		Universe(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Mario",
			description = "Lorem Ipsum dolor sit amet"
		),
		Universe(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Minecraft",
			description = "Lorem Ipsum dolor sit amet"
		),
		Universe(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Pokemon",
			description = "Lorem Ipsum dolor sit amet"
		)
	)

	val fighters = listOf(
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Mario",
			universe = "Mario",
			price = "14283",
			popular = true,
			rate = 5,
			downloads = "214",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		),
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Mario",
			universe = "Mario",
			price = "10953",
			popular = true,
			rate = 2,
			downloads = "4374",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		),
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Mario",
			universe = "Mario",
			price = "125",
			popular = true,
			rate = 3,
			downloads = "78",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		),
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Steve",
			universe = "Minecraft",
			price = "109283",
			popular = true,
			rate = 4,
			downloads = "12",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		),
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Luigi",
			universe = "Mario",
			price = "109283",
			popular = true,
			rate = 4,
			downloads = "1",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		),
		Fighter(
			objectId = "af98ehrf9a8hwef8hq3f",
			name = "Wario",
			universe = "Mario",
			price = "109283",
			popular = true,
			rate = 1,
			downloads = "980374",
			description = "Lorem Ipsum dolor sit amet",
			created_at = Date(),
			avatar = "https://myapp.koombea.com/smash/85.png"
		)
	)

}