package com.example.flashcardsapp.controller

import com.example.flashcardsapp.model.Flashcard

class FlashcardController {
    private val flashcards = mutableListOf<Flashcard>()
    init {
        // Initialize with some flashcards
        flashcards.add(Flashcard("Hello", "salut"))
        flashcards.add(Flashcard("World", "monde"))
    }
    fun addFlashcard(word: String, translation: String) {
        flashcards.add(Flashcard(word, translation))
    }

    fun getFlashcards(): List<Flashcard> {
        return flashcards
    }

    fun getFlashcardAt(index: Int): Flashcard? {
        return if (index in flashcards.indices) flashcards[index] else null
    }
}
