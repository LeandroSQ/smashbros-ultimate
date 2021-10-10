package quevedo.soares.leandro.techtest.extension

/**
 * Clamps a value given a min-max range
 * Ex: -1.clamp(0, 5) -> 0
 * Ex: 60.clamp(0, 5) -> 5
 *
 * @return Value within [min] - [max] range
 **/
fun Int.clamp(min: Int, max: Int) = if (this > max) max else if (this < min) min else this
