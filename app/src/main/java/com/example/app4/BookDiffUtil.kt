package com.example.app4

import androidx.recyclerview.widget.DiffUtil

class BookDiffUtil(val old: List<Book>, val new: List<Book>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return old.size
    }
    override fun getNewListSize(): Int {
        return new.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = this.old[oldItemPosition]
        val new = this.new[newItemPosition]
        return when {
            old.id != new.id -> false
            old.title != new.title -> false
            old.read != new.read -> false
            else -> true
        }
    }
}