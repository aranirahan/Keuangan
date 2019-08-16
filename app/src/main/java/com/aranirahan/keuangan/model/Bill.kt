package com.aranirahan.keuangan.model

data class Bill(
    val billId: Int? = null,
    val billBankName: String? = null,
    val billNumber: Int? = null,
    val billName: String? = null
) {
    companion object {
        val TABLE_BILL = "bill"
        val BILL_ID = "billId"
        val BANK_NAME = "billBankName"
        val NUMBER = "billNumber"
        val NAME = "billName"
    }
}