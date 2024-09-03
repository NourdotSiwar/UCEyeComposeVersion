package com.example.uceyecomposeversion.model.room

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromEye(eye: Eye): String {
        val selectedEye = when {
            eye.right -> "Right Eye"
            eye.left -> "Left Eye"
            eye.both -> "Both Eyes"
            else -> ""
        }
        return selectedEye
    }

    @TypeConverter
    fun toEye(jsonString: String): Eye {
        val eye = when (jsonString) {
            "Right Eye" -> Eye(left = false, right = true, both = false)
            "Left Eye" -> Eye(left = true, right = false, both = false)
            "Both Eyes" -> Eye(left = false, right = false, both = true)
            else -> Eye(left = false, right = false, both = false) // Default case
        }
        return eye
    }
}
