package me.honkling.detainment.lib

import org.bukkit.util.Transformation
import org.joml.Quaternionf
import org.joml.Vector3f

fun Transformation.setTranslation(translation: Vector3f): Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.setLeftRotation(leftRotation: Quaternionf): Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.setScale(scale: Vector3f): Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}

fun Transformation.setRightRotation(rightRotation: Quaternionf): Transformation {
    return Transformation(translation, leftRotation, scale, rightRotation)
}