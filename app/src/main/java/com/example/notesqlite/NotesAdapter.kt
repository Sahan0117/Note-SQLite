package com.example.notesqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

        private val db: NoteDataBaseHelper = NoteDataBaseHelper(context)

    // ViewHolder class to hold the views
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitle)  // Updated ID
        val contentTextView: TextView = itemView.findViewById(R.id.noteContent)  // Updated ID
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)  // Updated ID
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // Inflating the layout for each item and returning a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    // Binding data to each item view
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note Delete", Toast.LENGTH_SHORT).show()
        }
    }

    // Return the total count of items
    override fun getItemCount(): Int = notes.size

    // Function to refresh the list and notify the adapter
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}

