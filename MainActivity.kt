package com.example.wordle

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Word to guess
    private val wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    // Track the number of guesses
    private var currentGuess = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find UI elements
        val submitButton = findViewById<Button>(R.id.submitButton)
        val guessInput = findViewById<EditText>(R.id.guessInput)
        val guess1TextView = findViewById<TextView>(R.id.guess1)
        val guess1CheckTextView = findViewById<TextView>(R.id.guess1Check)
        val guess2TextView = findViewById<TextView>(R.id.guess2)
        val guess2CheckTextView = findViewById<TextView>(R.id.guess2Check)
        val guess3TextView = findViewById<TextView>(R.id.guess3)
        val guess3CheckTextView = findViewById<TextView>(R.id.guess3Check)
        val correctAnswerTextView = findViewById<TextView>(R.id.correctAnswerTextView)

        // Set up button click listener
        submitButton.setOnClickListener {
            val userGuess = guessInput.text.toString().uppercase()

            // Ensure the guess is a valid 4-letter word
            if (userGuess.length == 4) {
                when (currentGuess) {
                    1 -> {
                        // Update Guess #1
                        guess1TextView.text = userGuess
                        guess1CheckTextView.text = checkGuess(userGuess)
                    }
                    2 -> {
                        // Update Guess #2
                        guess2TextView.text = userGuess
                        guess2CheckTextView.text = checkGuess(userGuess)
                    }
                    3 -> {
                        // Update Guess #3
                        guess3TextView.text = userGuess
                        guess3CheckTextView.text = checkGuess(userGuess)
                    }
                }

                // Increment the guess counter
                currentGuess++

                // Clear the input field
                guessInput.text.clear()

                // Hide the keyboard after submitting
                hideKeyboard()

                // Disable input and show the correct answer if all guesses are used
                if (currentGuess > 3) {
                    guessInput.isEnabled = false
                    submitButton.isEnabled = false

                    // Show the correct answer
                    correctAnswerTextView.text = "CORRECT WORD: $wordToGuess"
                    correctAnswerTextView.visibility = TextView.VISIBLE
                }
            } else {
                guessInput.error = "Please enter a 4-letter word!"
            }
        }
    }

    // Function to check the user's guess
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in guess.indices) {
            when {
                guess[i] == wordToGuess[i] -> result += "O"  // Correct letter and position
                guess[i] in wordToGuess -> result += "+"     // Correct letter, wrong position
                else -> result += "X"                        // Incorrect letter
            }
        }
        return result
    }

    // Function to hide the keyboard
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
