package com.aranirahan.keuangan.feature

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aranirahan.keuangan.R
import com.aranirahan.keuangan.db.database
import com.aranirahan.keuangan.helper.getNumberIncome
import com.aranirahan.keuangan.model.Bill
import com.aranirahan.keuangan.model.Income
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var incomeList = arrayListOf<Income>()
    private var incomeIds: String = ""
    private var incomeFroms: String = ""
    private var incomeDescs: String = ""
    private var incomeAmounts: String = ""

    private var incomeNumbers: String = ""
    private var incomeDates: String = ""

    private val todayDate: String
        @SuppressLint("SimpleDateFormat")
        get() {
            val date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd MMM yyyy")
            return dateFormat.format(date)
        }

    private val todayDateForNumber: String
        @SuppressLint("SimpleDateFormat")
        get() {
            val date = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yymmdd")
            return dateFormat.format(date)
        }

    private var bill: Bill? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertBillDb()
        getOneBill()
        insertIncomeDb()
        getIncomeDb()
        initView()
        onClickBtnBill()
    }

    private fun insertBillDb() {
        database.use {
            insert(
                Bill.TABLE_BILL,
                Bill.BILL_ID to 1111,
                Bill.BANK_NAME to "Aran Bank",
                Bill.NUMBER to 1,
                Bill.NAME to "Markonah"
            )
        }
    }

    private fun getOneBill() {
        database.use {
            select(Bill.TABLE_BILL)
                .whereSimple("${Bill.BILL_ID} = ?", 1111.toString())
                .parseOpt(object : MapRowParser<Bill> {
                    override fun parseRow(columns: Map<String, Any?>): Bill {

                        val billId = columns.getValue(Bill.BILL_ID)
                        val billBankName = columns.getValue(Bill.BANK_NAME)
                        val billNumber = columns.getValue(Bill.NUMBER)
                        val billName = columns.getValue(Bill.NAME)

                        bill = Bill(
                            billId = billId?.toString()?.toInt(),
                            billBankName = billBankName?.toString(),
                            billNumber = billNumber?.toString()?.toInt(),
                            billName = billName?.toString()
                        )
                        return bill as Bill
                    }
                })
        }

    }

    private fun insertIncomeDb() {
        database.use {
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 1,
                Income.INCOME_FROM to bill?.billName,
                Income.DESC to "Buy Pencil",
                Income.AMOUNT to 1000,

                Income.NUMBER to getNumberIncome(todayDateForNumber),
                Income.DATE to todayDate,
                Income.BILL_ID to bill?.billId
            )
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 2,
                Income.INCOME_FROM to bill?.billName,
                Income.DESC to "Buy Pen",
                Income.AMOUNT to 2000,

                Income.NUMBER to getNumberIncome(todayDateForNumber),
                Income.DATE to todayDate,
                Income.BILL_ID to bill?.billId
            )
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 3,
                Income.INCOME_FROM to bill?.billName,
                Income.DESC to "Buy Paper",
                Income.AMOUNT to 3000,

                Income.NUMBER to getNumberIncome(todayDateForNumber),
                Income.DATE to todayDate,
                Income.BILL_ID to bill?.billId
            )

        }
    }

    private fun getIncomeDb() {
        try {
            database.use {
                select(
                    Income.TABLE_INCOME,
                    Income.INCOME_ID,
                    Income.INCOME_FROM,
                    Income.DESC,
                    Income.AMOUNT,

                    Income.NUMBER,
                    Income.DATE
                ).parseList(object : MapRowParser<List<Income>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Income> {
                        val incomeId = columns.getValue(Income.INCOME_ID)
                        val incomeFrom = columns.getValue(Income.INCOME_FROM)
                        val incomeDesc = columns.getValue(Income.DESC)
                        val incomeAmount = columns.getValue(Income.AMOUNT)

                        val incomeNumber = columns.getValue(Income.NUMBER)
                        val incomeDate = columns.getValue(Income.DATE)

                        val income = Income(
                            incomeId?.toString()?.toInt(),
                            incomeFrom?.toString(),
                            incomeDesc?.toString(),
                            incomeAmount?.toString()?.toInt(),

                            incomeNumber?.toString(),
                            incomeDate?.toString()
                        )

                        incomeList.add(income)
                        return incomeList
                    }
                })
            }
        } catch (e: SQLiteConstraintException) {
            toast("Failed get data cause $e")
        }
    }

    private fun initView() {
        incomeList.forEach {
            incomeIds += "${it.incomeId}\n"
            incomeFroms += "${it.incomeFrom}\n"
            incomeDescs += "${it.desc}\n"
            incomeAmounts += "${it.amount}\n"

            incomeNumbers += "${it.number}\n"
            incomeDates += "${it.date}\n"
        }

        tv_income_id.text = incomeIds
        tv_income_from.text = incomeFroms
        tv_income_desc.text = incomeDescs
        tv_income_amount.text = incomeAmounts

        tv_income_number.text = incomeNumbers
        tv_income_date.text = incomeDates

    }

    private fun onClickBtnBill() {
        btn_bill.setOnClickListener {
            startActivity<BillActivity>()
        }
    }

}
