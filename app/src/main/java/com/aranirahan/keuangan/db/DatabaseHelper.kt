package com.aranirahan.keuangan.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.aranirahan.keuangan.model.Income
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "LibraryDatabase", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.createTable(
            Income.TABLE_INCOME, true,
            Income.INCOME_ID to INTEGER + PRIMARY_KEY,
            Income.INCOME_FROM to TEXT,
            Income.DESC to TEXT,
            Income.AMOUNT to INTEGER
        )
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.dropTable(Income.TABLE_INCOME, true)
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)