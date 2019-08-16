package com.aranirahan.keuangan.feature

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aranirahan.keuangan.R
import com.aranirahan.keuangan.db.database
import com.aranirahan.keuangan.model.Income
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private var incomeList = arrayListOf<Income>()
    private var incomeIds: String = ""
    private var incomeFroms: String = ""
    private var incomeDescs: String = ""
    private var incomeAmounts: String = ""

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
                Income.AMOUNT to 1000
            )
            insert(
                Income.TABLE_INCOME,
                Income.INCOME_ID to 2,
                Income.INCOME_FROM to "Customer B",
                Income.DESC to "Buy Pen",
                Income.AMOUNT to 2000
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
                    Income.AMOUNT
                ).parseList(object : MapRowParser<List<Income>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Income> {
                        val incomeId = columns.getValue(Income.INCOME_ID)
                        val incomeFrom = columns.getValue(Income.INCOME_FROM)
                        val incomeDesc = columns.getValue(Income.DESC)
                        val incomeAmount = columns.getValue(Income.AMOUNT)

                        val income = Income(
                            incomeId.toString().toInt(),
                            incomeFrom.toString(),
                            incomeDesc.toString(),
                            incomeAmount.toString().toInt()
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
        }

        tv_income_id.text = incomeIds
        tv_income_from.text = incomeFroms
        tv_income_desc.text = incomeDescs
        tv_income_amount.text = incomeAmounts
    }
}
