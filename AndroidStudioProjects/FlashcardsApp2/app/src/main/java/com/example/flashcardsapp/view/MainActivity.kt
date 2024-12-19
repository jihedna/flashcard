package com.example.flashcardsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardsapp.R
import com.example.flashcardsapp.model.Flashcard
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val flashcards = mutableListOf<Flashcard>()
    private lateinit var flashcardTextView: TextView
    private var currentIndex = 0
    private lateinit var logoutButton: Button

    private lateinit var addFlashcardLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        flashcardTextView = findViewById(R.id.textViewFlashcard)
        val flipButton: Button = findViewById(R.id.buttonFlip)
        val addFlashcardButton: Button = findViewById(R.id.buttonAddFlashcard)
        val startQuizButton: Button = findViewById(R.id.buttonStartQuiz)
        logoutButton = findViewById(R.id.buttonLogout)
        val buttonViewFlashcards: Button = findViewById(R.id.buttonViewFlashcards)

        flashcards.add(Flashcard("Hello", "salut"))
        flashcards.add(Flashcard("World", "monde"))

        addFlashcardLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val word = data?.getStringExtra("word")
                val translation = data?.getStringExtra("translation")
                if (word != null && translation != null) {
                    flashcards.add(Flashcard(word, translation))
                }
            }
        }

        showFlashcard()

        flipButton.setOnClickListener {
            if (flashcardTextView.text == flashcards[currentIndex].word) {
                flashcardTextView.text = flashcards[currentIndex].translation
            } else {
                currentIndex = (currentIndex + 1) % flashcards.size
                showFlashcard()
            }
        }

        addFlashcardButton.setOnClickListener {
            val intent = Intent(this, AddFlashcardActivity::class.java)
            addFlashcardLauncher.launch(intent)
        }

        startQuizButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            val flashcardArrayList = ArrayList(flashcards)
            intent.putParcelableArrayListExtra("flashcards", flashcardArrayList)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonViewFlashcards.setOnClickListener {
            val intent = Intent(this, FlashcardListActivity::class.java)
            intent.putParcelableArrayListExtra("flashcards", ArrayList(flashcards))
            startActivity(intent)
        }
    }

    private fun showFlashcard() {
        if (flashcards.isNotEmpty()) {
            flashcardTextView.text = flashcards[currentIndex].word
        }
    }
}
