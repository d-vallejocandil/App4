package com.example.app4

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseManager(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Book.SQL_CREATE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
        onDestroy(db)
    }
    fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(Book.SQL_DROP)
    }
    companion object {
        const val DATABASE_NAME = "Book.db"
        const val DATABASE_VERSION = 1
    }
}