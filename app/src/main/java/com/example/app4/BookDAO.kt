package com.example.app4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class BookDAO(val context: Context) {
    lateinit var db: SQLiteDatabase
    fun open() {
        db = DatabaseManager(context).writableDatabase
    }
    fun close() {
        db.close()
    }
    fun values(book: Book): ContentValues {
        val values = ContentValues()
        values.put(Book.COLUMN_NAME_TITLE, book.title)
        values.put(Book.COLUMN_NAME_READ, book.read)
        return values
    }
    fun cursor(cursor: Cursor): Book {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Book.COLUMN_NAME_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Book.COLUMN_NAME_TITLE))
        val read = cursor.getInt(cursor.getColumnIndexOrThrow(Book.COLUMN_NAME_READ)) != 0
        return Book(id, title, read)
    }
    fun insert(book: Book) {
        open()
        val values = values(book)
        try {
            db.insertOrThrow(Book.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("DB", e.toString())
        } finally {
            close()
        }
    }
    fun delete(id: Long) {
        open()
        try {
            db.delete(Book.TABLE_NAME, "${Book.COLUMN_NAME_ID} = $id", null)
        } catch (e: Exception) {
            Log.e("DB", e.toString())
        } finally {
            close()
        }
    }
    fun delete(book: Book) {
        delete(book.id)
    }
    fun update(book: Book) {
        open()
        val values = values(book)
        try {
            db.update(Book.TABLE_NAME, values, "${Book.COLUMN_NAME_ID} = ${book.id}", null)
        } catch (e: Exception) {
            Log.e("DB", e.toString())
        } finally {
            close()
        }
    }
    fun get(id: Long): Book? {
        open()
        var book: Book? = null
        val cursor = db.query(Book.TABLE_NAME, null, "${Book.COLUMN_NAME_ID} = $id", null, null, null, null)
        try {
            while(cursor.moveToNext()) {
                book = cursor(cursor)
            }
        } catch (e: Exception) {
            Log.e("DB", e.toString())
        } finally {
            close()
        }
        return book
    }
    fun get(): List<Book> {
        open()
        var books: MutableList<Book> = mutableListOf()
        val cursor = db.query(Book.TABLE_NAME, null, null, null, null, null, null)
        try {
            while (cursor.moveToNext()) {
                books.add(cursor(cursor))
            }
        } catch (e: Exception) {
            Log.e("DB", e.toString())
        } finally {
            close()
        }
        return books
    }
}