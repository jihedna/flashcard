package com.example.flashcardsapp.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardsapp.R
import com.example.flashcardsapp.model.Flashcard

class FlashcardAdapter(private val flashcards: List<Flashcard>) :
    RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    // ViewHolder to represent each flashcard
    class FlashcardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.textViewWord)
        val translationTextView: TextView = itemView.findViewById(R.id.textViewTranslation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.wordTextView.text = flashcard.word
        holder.translationTextView.text = flashcard.translation
    }

    override fun getItemCount(): Int = flashcards.size
}
