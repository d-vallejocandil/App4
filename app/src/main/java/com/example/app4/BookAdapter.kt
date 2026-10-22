package com.example.app4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app4.databinding.BookBinding

class BookAdapter(var books: List<Book>): RecyclerView.Adapter<BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.render(book)
    }
    override fun getItemCount(): Int {
        return books.size
    }
}
class BookViewHolder(val binding: BookBinding): RecyclerView.ViewHolder(binding.root) {
    fun render(book: Book) {
        binding.check.isChecked = book.read
        binding.title.text = book.title
    }
}