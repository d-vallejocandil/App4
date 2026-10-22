package com.example.app4

data class Book(val id: Long, var title: String, var read: Boolean = false) {
    companion object {
        const val TABLE_NAME = "Book"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_READ = "read"
        const val SQL_CREATE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME_TITLE TEXT, " +
                "$COLUMN_NAME_READ BOOLEAN)"
        const val SQL_DROP = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}