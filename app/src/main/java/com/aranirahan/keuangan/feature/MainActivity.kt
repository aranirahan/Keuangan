package com.aranirahan.keuangan.feature

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aranirahan.keuangan.R
import com.aranirahan.keuangan.db.database
import com.aranirahan.keuangan.model.Income
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertDb()
        getDb()
        initView()
    }

    private fun insertDb(){
        database.use {
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 1,
                Income.INCOME_FROM to "Customer A",
                Income.DESC to "Buy Pencil",
                Income.AMOUNT to 1000,

                Income.NUMBER to 1,
                Income.DATE to todayDate
            )
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 2,
                Income.INCOME_FROM to "Customer B",
                Income.DESC to "Buy Pen",
                Income.AMOUNT to 2000,

                Income.NUMBER to 2,
                Income.DATE to todayDate
            )
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 3,
                Income.INCOME_FROM to "Customer C",
                Income.DESC to "Buy Paper",
                Income.AMOUNT to 3000,

                Income.NUMBER to 3,
                Income.DATE to todayDate
            )

        }
    }

    private fun getDb(){
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

                            incomeNumber?.toString()?.toInt(),
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

    private fun initView(){
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
}
