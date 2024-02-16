package me.honkling.detainment.lib

fun getTime(timer: Int): String {
    val first = timer / 60
    val second = timer - first * 60

    return padTime(first, second)
}

fun padTime(first: Int, second: Int): String {
    val paddedFirst = first.toString().padStart(2, '0')
    val paddedSecond = second.toString().padStart(2, '0')

    return "$paddedFirst:$paddedSecond"
}