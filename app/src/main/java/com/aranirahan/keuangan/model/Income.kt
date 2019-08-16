package com.aranirahan.keuangan.model

data class Income(
    val incomeId: Int? = null,
    val incomeFrom: String? = null,
    val desc: String? = null,
    val amount: Int? = null,

    val number: String? = null,
    val date: String? = null,

    val billId: Int? = null
) {
    companion object {
        val TABLE_INCOME = "income"
        val INCOME_ID = "incomeId"
        val INCOME_FROM = "incomeFrom"
        val DESC = "desc"
        val AMOUNT = "amount"

        val NUMBER = "number"
        val DATE = "date"

        val BILL_ID = "billId"
    }
}