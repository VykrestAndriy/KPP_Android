package com.example.lb_android

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log10
import kotlin.math.pow

class CalculatorActivity : AppCompatActivity() {

    private lateinit var calculatorResultTextView: TextView
    private var currentInput: StringBuilder = StringBuilder()
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        calculatorResultTextView = findViewById(R.id.calculatorResultTextView)

        val buttons = listOf(
            R.id.clearButton, R.id.decimalButton, R.id.addButton, R.id.subtractButton,
            R.id.multiplyButton, R.id.divideButton, R.id.powerButton, R.id.logarithmButton,
            R.id.digitZeroButton, R.id.digitOneButton, R.id.digitTwoButton, R.id.digitThreeButton,
            R.id.digitFourButton, R.id.digitFiveButton, R.id.digitSixButton, R.id.digitSevenButton,
            R.id.digitEightButton, R.id.digitNineButton, R.id.equalsButton
        )

        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button
        val text = button.text.toString()

        when (text) {
            in "0".."9" -> appendNumber(text)
            "." -> appendDot()
            "+" -> performOperation("+")
            "-" -> performOperation("-")
            "×" -> performOperation("×")
            "÷" -> performOperation("÷")
            "^" -> performOperation("^")
            "log" -> performLogarithm()
            "C" -> clearInput()
            "=" -> calculateResult()
        }
    }

    private fun appendNumber(number: String) {
        currentInput.append(number)
        updateResultTextView()
    }

    private fun appendDot() {
        if (!currentInput.contains(".")) {
            currentInput.append(".")
            updateResultTextView()
        }
    }

    private fun performOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toString().toDouble()
            operator = op
            currentInput.clear()
            updateResultTextView()
        }
    }

    private fun performLogarithm() {
        if (currentInput.isNotEmpty()) {
            try {
                val number = currentInput.toString().toDouble()
                val result = log10(number)
                currentInput.clear()
                currentInput.append(result.toString())
                updateResultTextView()
                operand1 = null
                operator = null
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Невірний формат числа", Toast.LENGTH_SHORT).show()
                clearInput()
            }
        }
    }

    private fun clearInput() {
        currentInput.clear()
        operand1 = null
        operator = null
        updateResultTextView("0")
    }

    private fun calculateResult() {
        if (operand1 != null && operator != null && currentInput.isNotEmpty()) {
            try {
                val operand2 = currentInput.toString().toDouble()
                val result = when (operator) {
                    "+" -> operand1!! + operand2
                    "-" -> operand1!! - operand2
                    "×" -> operand1!! * operand2
                    "÷" -> {
                        if (operand2 == 0.0) {
                            Toast.makeText(this, "Ділення на нуль!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        operand1!! / operand2
                    }
                    "^" -> operand1!!.pow(operand2)
                    else -> return
                }
                currentInput.clear()
                currentInput.append(result.toString())
                updateResultTextView()
                operand1 = null
                operator = null
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Невірний формат числа", Toast.LENGTH_SHORT).show()
                clearInput()
            }
        }
    }

    private fun updateResultTextView(text: String = currentInput.toString()) {
        calculatorResultTextView.text = text.ifEmpty { "0" }
    }
}