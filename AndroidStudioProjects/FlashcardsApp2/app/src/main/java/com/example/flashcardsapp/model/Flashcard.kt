package com.example.flashcardsapp.model

import android.os.Parcel
import android.os.Parcelable
data class Flashcard(
    val word: String = "",       // Default value ensures no-argument constructor
    val translation: String = "" // Default value ensures no-argument constructor
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",  // Default to empty string if null
        parcel.readString() ?: ""   // Default to empty string if null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(translation)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Flashcard> {
        override fun createFromParcel(parcel: Parcel): Flashcard {
            return Flashcard(parcel)
        }

        override fun newArray(size: Int): Array<Flashcard?> {
            return arrayOfNulls(size)
        }
    }
}
