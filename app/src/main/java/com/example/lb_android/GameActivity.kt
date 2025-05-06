package com.example.lb_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var hintView: TextView
    private lateinit var guessInput: EditText
    private lateinit var actionControl: Button
    private lateinit var gameVariant: String
    private var lowerBound = 1
    private var upperBound = 100
    private var playerSecretNum: Int? = null
    private var targetNumber = 0

    private lateinit var increaseControl: Button
    private lateinit var decreaseControl: Button
    private lateinit var successControl: Button
    private lateinit var beginAction: Button
    private lateinit var computerTry: Button
    private lateinit var playerTry: Button
    private lateinit var modeContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        beginAction = findViewById(R.id.beginAction)
        computerTry = findViewById(R.id.computerTry)
        playerTry = findViewById(R.id.playerTry)
        hintView = findViewById(R.id.hintView)
        guessInput = findViewById(R.id.guessInput)
        actionControl = findViewById(R.id.actionControl)
        increaseControl = findViewById(R.id.increaseControl)
        decreaseControl = findViewById(R.id.decreaseControl)
        successControl = findViewById(R.id.successControl)
        modeContainer = findViewById(R.id.modeContainer)

        beginAction.setOnClickListener {
            showModeSelection()
        }
    }

    private fun showModeSelection() {
        beginAction.visibility = View.GONE
        modeContainer.visibility = View.VISIBLE

        computerTry.setOnClickListener {
            startGameLogic("computer")
        }

        playerTry.setOnClickListener {
            startGameLogic("player")
        }
    }

    private fun startGameLogic(modeKey: String) {
        gameVariant = modeKey

        modeContainer.visibility = View.GONE;
        hintView.visibility = View.VISIBLE
        guessInput.visibility = View.VISIBLE


        if (gameVariant == "computer") {
            startGameForAi()
        } else if (gameVariant == "player") {
            startGameForHuman()
        }
    }

    private fun startGameForAi() {
        targetNumber = (1..100).random()
        hintView.text = "Введіть ваше число:"
        guessInput.hint = "Ваше число"
        actionControl.text = "Підтвердити"
        actionControl.visibility = View.VISIBLE

        actionControl.setOnClickListener {
            handleAiGuess()
        }
    }

    private fun handleAiGuess() {
        val guessInput = guessInput.text.toString()
        val guessedValue = guessInput.toIntOrNull()
        if (guessedValue != null) {
            if (guessedValue > targetNumber) {
                hintView.text = "Загадане число менше"
            } else if (guessedValue < targetNumber) {
                hintView.text = "Загадане число більше"
            } else {
                hintView.text = "Ви вгадали!"
                Handler(Looper.getMainLooper()).postDelayed({
                    startGameForAi()
                }, 5000)
            }
        } else {
            hintView.text = "Введіть число"
        }
    }

    private fun startGameForHuman() {
        hintView.text = "Загадайте число від 1 до 100 та введіть його:"
        guessInput.hint = "Ваше число"
        actionControl.text = "Готово"
        actionControl.visibility = View.VISIBLE

        actionControl.setOnClickListener {
            if (playerSecretNum == null) {
                val secretInput = guessInput.text.toString()
                val secretVal = secretInput.toIntOrNull()
                if (secretVal != null && secretVal in 1..100) {
                    playerSecretNum = secretVal
                    hintView.text = "Я починаю вгадувати..."
                    guessInput.visibility = View.GONE
                    actionControl.visibility = View.GONE
                    increaseControl.visibility = View.VISIBLE
                    decreaseControl.visibility = View.VISIBLE
                    successControl.visibility = View.VISIBLE
                    makeAiAttempt()
                } else {
                    hintView.text = "Будь ласка, введіть число від 1 до 100"
                }
            } else {
                handleHumanHint()
            }
        }

        increaseControl.setOnClickListener {
            lowerBound = targetNumber + 1
            makeAiAttempt()
        }

        decreaseControl.setOnClickListener {
            upperBound = targetNumber - 1
            makeAiAttempt()
        }

        successControl.setOnClickListener {
            hintView.text = "Ура, я молодець!"
            guessInput.visibility = View.GONE
            increaseControl.visibility = View.GONE
            decreaseControl.visibility = View.GONE
            successControl.visibility = View.GONE
            Handler(Looper.getMainLooper()).postDelayed({
                hintView.text = "Загадайте число від 1 до 100 та введіть його:"
                guessInput.visibility = View.VISIBLE
                guessInput.hint = "Ваше число"
                actionControl.visibility = View.VISIBLE
                actionControl.text = "Готово"
                lowerBound = 1
                upperBound = 100
                playerSecretNum = null
            }, 5000)
        }
    }

    private fun makeAiAttempt() {
        if (lowerBound <= upperBound) {
            targetNumber = (lowerBound..upperBound).random()
            hintView.text = "Моя спроба: $targetNumber"
        } else {
            hintView.text = "Я не можу вгадати! Ви помилилися з підказками."
        }
    }

    private fun handleHumanHint() {
        if (playerSecretNum != null) {
            val hintGiven = actionControl.text.toString().toLowerCase()
            if (hintGiven == "більше") {
                lowerBound = targetNumber + 1
                makeAiAttempt()
            } else if (hintGiven == "менше") {
                upperBound = targetNumber - 1
                makeAiAttempt()
            } else if (hintGiven == "вгадав") {
                hintView.text = "Ура, я молодець!"
                guessInput.visibility = View.GONE
                increaseControl.visibility = View.GONE
                decreaseControl.visibility = View.GONE
                successControl.visibility = View.GONE
                Handler(Looper.getMainLooper()).postDelayed({
                    hintView.text = "Загадайте число від 1 до 100 та введіть його:"
                    guessInput.visibility = View.VISIBLE
                    guessInput.hint = "Ваше число"
                    actionControl.visibility = View.VISIBLE
                    actionControl.text = "Готово"
                    lowerBound = 1
                    upperBound = 100
                    playerSecretNum = null
                }, 5000)
            } else {
                hintView.text = "Будь ласка, введіть 'більше', 'менше' або 'вгадав'"
            }
        }
    }
}