package com.aranirahan.keuangan.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class MySqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "mydb") {

    companion object {
        private var instance: MySqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MySqlHelper {
            if (instance == null) {
                instance =
                    MySqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}
//
//val Context.database: MySqlHelper
//    get() = MySqlHelper.getInstance(applicationContext)