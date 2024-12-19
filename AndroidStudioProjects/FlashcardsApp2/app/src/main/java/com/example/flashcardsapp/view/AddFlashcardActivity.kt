package com.example.flashcardsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardsapp.R
import com.example.flashcardsapp.model.Flashcard
import com.google.firebase.database.FirebaseDatabase

class AddFlashcardActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flashcard)

        // Initialize Firebase with the correct database URL
        database = FirebaseDatabase.getInstance("https://flashcard2-1b76a-default-rtdb.firebaseio.com/")

        val editTextWord: EditText = findViewById(R.id.editTextWord)
        val editTextTranslation: EditText = findViewById(R.id.editTextTranslation)
        val saveButton: Button = findViewById(R.id.buttonSaveFlashcard)

        saveButton.setOnClickListener {
            saveButton.isEnabled = false

            val word = editTextWord.text.toString()
            val translation = editTextTranslation.text.toString()

            if (word.isNotEmpty() && translation.isNotEmpty()) {
                val flashcard = Flashcard(word, translation)
                val flashcardsRef = database.getReference("flashcards")
                val key = flashcardsRef.push().key ?: ""

                flashcardsRef.child(key).setValue(flashcard)
                    .addOnSuccessListener {
                        // Flashcard saved to Firebase successfully
                        Toast.makeText(this, "Flashcard saved!", Toast.LENGTH_SHORT).show()

                        // Send broadcast or Intent to update the list in FlashcardListActivity
                        val intent = Intent()
                        intent.action = "com.example.flashcardsapp.ACTION_FLASHCARD_ADDED"
                        sendBroadcast(intent)

                        // Close AddFlashcardActivity
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    .addOnCompleteListener {
                        saveButton.isEnabled = true // Re-enable button
                    }
            } else {
                saveButton.isEnabled = true // Re-enable button
                Toast.makeText(this, "Please fill in both fields.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
