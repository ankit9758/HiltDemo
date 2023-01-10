package com.example.hiltdemo.utils

import android.widget.EditText
import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("stringToPrice")
    @JvmStatic fun floatToString(time:Float): String {
        // Converts long to String.
      return time.toInt().toString()
    }

    @JvmStatic fun stringToPrice(
        value: String
    ): Float {
       return value.toFloat()
    }
}