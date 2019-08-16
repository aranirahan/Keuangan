package com.aranirahan.keuangan.feature

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aranirahan.keuangan.R
import com.aranirahan.keuangan.db.database
import com.aranirahan.keuangan.model.Bill
import kotlinx.android.synthetic.main.activity_bill.*
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast


class BillActivity : AppCompatActivity() {

    private var billList = arrayListOf<Bill>()

    private var billIds = ""
    private var billBankNames = ""
    private var billNumbers = ""
    private var billNames = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        getBillDb()
        initView()
    }

    private fun getBillDb() {
        try {
            database.use {
                select(
                    Bill.TABLE_BILL,
                    Bill.BILL_ID,
                    Bill.BANK_NAME,
                    Bill.NUMBER,
                    Bill.NAME
                ).parseList(object : MapRowParser<List<Bill>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Bill> {
                        val billId = columns.getValue(Bill.BILL_ID)
                        val billBankName = columns.getValue(Bill.BANK_NAME)
                        val billNumber = columns.getValue(Bill.NUMBER)
                        val billName = columns.getValue(Bill.NAME)

                        val bill = Bill(
                            billId?.toString()?.toInt(),
                            billBankName?.toString(),
                            billNumber?.toString()?.toInt(),
                            billName?.toString()
                        )

                        billList.add(bill)
                        return billList
                    }
                })
            }
        } catch (e: SQLiteConstraintException) {
            toast("Failed get data cause $e")
        }
    }

    private fun initView() {

        billList.forEach {
            billIds += "${it.billId}\n"
            billBankNames += "${it.billBankName}\n"
            billNumbers += "${it.billNumber}\n"
            billNames += "${it.billName}\n"
        }

        tv_number.text = billNumbers
        tv_name.text = billNames
        tv_bill_id.text = billIds
        tv_bill_bank_name.text = billBankNames
    }
}