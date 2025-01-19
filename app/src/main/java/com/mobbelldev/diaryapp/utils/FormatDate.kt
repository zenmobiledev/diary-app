package com.mobbelldev.diaryapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatDate(inputDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("EEE, dd MM yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(inputDate)
            return date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            return "Invalid Date"
        }
    }
}