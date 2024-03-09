package me.honkling.detainment.schedule

fun Int.getHours(): Int {
    return div(60)
}

fun Int.getMinutes(): Int {
    return mod(60)
}

fun Int.getFormattedHours(): String {
    return getHours().toString().padStart(2, '0')
}

fun Int.getFormattedMinutes(): String {
    return getMinutes().toString().padStart(2, '0')
}