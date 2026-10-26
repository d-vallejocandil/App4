package com.example.app4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.app4.databinding.ActivityMainBinding
import com.example.app4.databinding.BookDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var dao: BookDAO
    var books = listOf<Book>()
    lateinit var adapter: BookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dao = BookDAO(this)
        books = dao.get()
        adapter = BookAdapter(books, ::check, ::edit, ::delete)
        binding.recycler.adapter = adapter
        configureGestures()
        binding.add.setOnClickListener {
            val book = Book(title="")
            bookDialog(book)
        }
    }
    fun update() {
        books = dao.get()
        adapter.update(books.map { it.copy() } as MutableList<Book>) // TODO
    }
    override fun onResume() {
        super.onResume()
        update()
    }
    fun check(position: Int) {
        val book = books[position]
        book.read = !book.read
        dao.update(book)
        update()
    }
    fun edit(position: Int) {
        val book = books[position]
        bookDialog(book)
    }
    fun delete(position: Int) {
        val book = books[position]
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle("Delete ${book.title}")
            .setMessage("Are you sure?")
            .setPositiveButton("Delete") { dialog, which ->
                dao.delete(book)
                update()
            }
            .setNegativeButton("Cancel") { dialog, which ->

            }
        val dialog = builder.create()
        dialog.show()
    }
    fun bookDialog(book: Book) {
        val binding = BookDialogBinding.inflate(layoutInflater)
        binding.text.editText!!.setText(book.title)
        val title = if (book.id == (-1).toLong()) "Add" else "Edit"
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setView(binding.root)
            .setPositiveButton("Save") { dialog, which ->

            }
            .setNegativeButton("Cancel") { dialog, which ->

            }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val title = binding.text.editText!!.text.toString()
            if(title == "") {
                binding.text.error = "Error"
            } else {
                book.title = title
                dao.save(book)
                update()
                dialog.dismiss()
            }
        }
    }

    fun configureGestures() {
        val callback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = viewHolder.bindingAdapterPosition
                val to = target.bindingAdapterPosition
                if (from == RecyclerView.NO_POSITION || to == RecyclerView.NO_POSITION) return false
                adapter.move(from, to)
                return true
            }
            override fun isLongPressDragEnabled(): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.START) {
                    delete(viewHolder.bindingAdapterPosition)
                    adapter.notifyItemChanged(viewHolder.bindingAdapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recycler)
        adapter.startDrag = { viewHolder ->
            itemTouchHelper.startDrag(viewHolder)
        }
    }
}