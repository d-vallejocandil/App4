package com.example.app4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app4.databinding.BookBinding

class BookAdapter(var books: List<Book>, val checkListener: (Int) -> Unit, val editListener: (Int) -> Unit, val deleteListener: (Int) -> Unit): RecyclerView.Adapter<BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.render(book)
        holder.binding.check.setOnClickListener {
            checkListener(holder.bindingAdapterPosition)
        }
        holder.binding.edit.setOnClickListener {
            editListener(holder.bindingAdapterPosition)
        }
        holder.binding.delete.setOnClickListener {
            deleteListener(holder.bindingAdapterPosition)
        }
    }
    override fun getItemCount(): Int {
        return books.size
    }
    fun update(books: List<Book>) {
        val diff = BookDiffUtil(this.books, books)
        val result = DiffUtil.calculateDiff(diff)
        this.books = books
        result.dispatchUpdatesTo(this)
    }
}
class BookViewHolder(val binding: BookBinding): RecyclerView.ViewHolder(binding.root) {
    fun render(book: Book) {
        binding.check.isChecked = book.read
        binding.title.text = book.title
    }
}