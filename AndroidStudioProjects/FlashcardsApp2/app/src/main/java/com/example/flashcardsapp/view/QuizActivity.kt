package com.example.flashcardsapp.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardsapp.R
import com.example.flashcardsapp.model.Flashcard

class QuizActivity : AppCompatActivity() {

    private lateinit var flashcards: MutableList<Flashcard>
    private lateinit var flashcardTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        flashcards = intent.getParcelableArrayListExtra("flashcards") ?: mutableListOf()

        flashcardTextView = findViewById(R.id.textViewFlashcard)
        answerEditText = findViewById(R.id.editTextAnswer)
        submitButton = findViewById(R.id.buttonSubmit)

        showFlashcard()

        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().trim()
            if (userAnswer.isNotEmpty()) {
                if (userAnswer.equals(flashcards[currentIndex].translation, ignoreCase = true)) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                    currentIndex++
                    if (currentIndex < flashcards.size) {
                        showFlashcard()
                        answerEditText.text.clear()
                    } else {
                        Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Answer is wrong. Try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFlashcard() {
        if (currentIndex < flashcards.size) {
            flashcardTextView.text = flashcards[currentIndex].word
        }
    }
}
