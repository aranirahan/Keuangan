package com.aranirahan.keuangan.model

data class Income(
    val incomeId: Int,
    val incomeFrom: String,
    val desc: String,
    val amount: Int
) {
    companion object {
        val TABLE_INCOME = "income"
        val INCOME_ID = "incomeId"
        val INCOME_FROM = "incomeFrom"
        val DESC = "desc"
        val AMOUNT = "amount"
    }
}