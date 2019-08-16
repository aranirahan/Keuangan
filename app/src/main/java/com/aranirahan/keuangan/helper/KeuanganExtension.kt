package com.aranirahan.keuangan.helper

import android.content.Context

fun Context.getNumberIncome(date: String): String {
    val sharedPreference = getSharedPreferences(date, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()

    val number = (sharedPreference?.getInt("numberIncome", 0) ?: 0) + 1
    editor.putInt("numberIncome", number)
    editor.apply()

    return "UM/$date/$number"
}