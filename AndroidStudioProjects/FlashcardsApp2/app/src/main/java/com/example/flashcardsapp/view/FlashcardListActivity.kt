package com.example.flashcardsapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardsapp.R
import com.example.flashcardsapp.model.Flashcard
import com.example.flashcardsapp.controller.FlashcardAdapter
import com.google.firebase.database.*

class FlashcardListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var flashcardAdapter: FlashcardAdapter
    private var flashcards = mutableListOf<Flashcard>()
    private lateinit var database: FirebaseDatabase
    private lateinit var flashcardsRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_list)

        // Initialize Firebase with the correct database URL
        database = FirebaseDatabase.getInstance("https://flashcard2-1b76a-default-rtdb.firebaseio.com/")
        flashcardsRef = database.getReference("flashcards")

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewFlashcards)
        recyclerView.layoutManager = LinearLayoutManager(this)
        flashcardAdapter = FlashcardAdapter(flashcards)
        recyclerView.adapter = flashcardAdapter

        // Listen for updates (new flashcard added) from AddFlashcardActivity
        val filter = IntentFilter("com.example.flashcardsapp.ACTION_FLASHCARD_ADDED")
        registerReceiver(flashcardAddedReceiver, filter)

        // Fetch initial list of flashcards from Firebase
        fetchFlashcards()
    }

    private fun fetchFlashcards() {
        flashcardsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                flashcards.clear()
                for (dataSnapshot in snapshot.children) {
                    val flashcard = dataSnapshot.getValue(Flashcard::class.java)
                    flashcard?.let { flashcards.add(it) }
                }
                flashcardAdapter.notifyDataSetChanged() // Update adapter with new data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FlashcardListActivity, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Receiver to update the list when a new flashcard is added
    private val flashcardAddedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Refresh the list after receiving the broadcast
            fetchFlashcards()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(flashcardAddedReceiver) // Unregister the receiver to prevent memory leaks
    }
}
