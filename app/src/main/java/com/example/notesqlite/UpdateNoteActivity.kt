package com.example.notesqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesqlite.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NoteDataBaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseHelper(this)

        // Get note ID from intent
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish() // Invalid note ID, close the activity
            return
        }

        // Fetch note from database
        val note = db.getNoteByID(noteId)

        if (note != null) {
            // Set existing note title and content in the text fields
            binding.updateTitleEditText.setText(note.title)
            binding.updateContentEditText.setText(note.content)
        } else {
            // If note is null, close the activity
            finish()
            return
        }

        // Handle save button click
        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            // Update the note with the new title and content
            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)

            // Show confirmation message
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()

            // Close the activity
            finish()
        }
    }
}
