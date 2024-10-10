package com.example.notesqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesqlite.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NoteDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDataBaseHelper(this)

        binding.saveButton.setOnClickListener {
            // Correct way to retrieve text from EditText
            val title = binding.titleEditText.text.toString()  // Fixed here
            val content = binding.contentEditText.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                // Create the note and insert it into the database
                val note = Note(0, title, content)
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            } else {
                // Show error if title or content is empty
                Toast.makeText(this, "Title or Content cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
