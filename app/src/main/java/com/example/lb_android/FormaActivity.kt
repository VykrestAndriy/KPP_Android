package com.example.lb_android

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormaActivity : AppCompatActivity() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText
    private lateinit var desiredSalaryInput: TextInputEditText
    private lateinit var question1Group: RadioGroup
    private lateinit var question2Group: RadioGroup
    private lateinit var question3Group: RadioGroup
    private lateinit var question4Group: RadioGroup
    private lateinit var question5Group: RadioGroup
    private lateinit var hasExperienceCheckbox: CheckBox
    private lateinit var isTeamPlayerCheckbox: CheckBox
    private lateinit var canTravelCheckbox: CheckBox
    private lateinit var submitButton: Button
    private lateinit var resultText: TextView
    private lateinit var contactInfoText: TextView

    private val passingScore = 10
    private val questionPoints = 2
    private val skillPoints = 1

    private val correctAnswers = mapOf(
        R.id.option1B to "B",
        R.id.option2C to "C",
        R.id.option3B to "B",
        R.id.option4B to "B",
        R.id.option5B to "B"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forma)

        nameInput = findViewById(R.id.nameEditText)
        ageInput = findViewById(R.id.ageEditText)
        desiredSalaryInput = findViewById(R.id.salaryEditText)
        question1Group = findViewById(R.id.question1RadioGroup)
        question2Group = findViewById(R.id.question2RadioGroup)
        question3Group = findViewById(R.id.question3RadioGroup)
        question4Group = findViewById(R.id.question4RadioGroup)
        question5Group = findViewById(R.id.question5RadioGroup)
        hasExperienceCheckbox = findViewById(R.id.experienceCheckbox)
        isTeamPlayerCheckbox = findViewById(R.id.teamworkCheckbox)
        canTravelCheckbox = findViewById(R.id.travelCheckbox)
        submitButton = findViewById(R.id.submitButton)
        resultText = findViewById(R.id.resultTextView)
        contactInfoText = findViewById(R.id.contactsTextView)

        submitButton.setOnClickListener {
            val score = calculateTotalScore()
            resultText.visibility = View.VISIBLE
            contactInfoText.visibility = View.GONE

            val fullName = nameInput.text.toString()
            val age = ageInput.text.toString()
            val salary = desiredSalaryInput.text.toString()

            if (fullName.isNotBlank() && age.isNotBlank() && salary.isNotBlank()) {
                if (score >= passingScore) {
                    resultText.text = "$fullName, вітаємо! Ви набрали $score балів і успішно пройшли попередню оцінку."
                    contactInfoText.visibility = View.VISIBLE
                } else {
                    resultText.text = "$fullName, на жаль, ви не пройшли попередню оцінку. Набрано $score з $passingScore балів."
                }
            } else {
                resultText.text = "Будь ласка, заповніть всі особисті дані перед здачею тесту."
            }
        }
    }

    private fun calculateTotalScore(): Int {
        var score = 0

        score += checkQuestion(question1Group, R.id.option1A, R.id.option1B, R.id.option1C, correctAnswers[R.id.option1B])
        score += checkQuestion(question2Group, R.id.option2A, R.id.option2B, R.id.option2C, correctAnswers[R.id.option2A])
        score += checkQuestion(question3Group, R.id.option3A, R.id.option3B, R.id.option3C, correctAnswers[R.id.option3B])
        score += checkQuestion(question4Group, R.id.option4A, R.id.option4B, R.id.option4C, correctAnswers[R.id.option4A])
        score += checkQuestion(question5Group, R.id.option5A, R.id.option5B, R.id.option5C, correctAnswers[R.id.option5C])

        if (hasExperienceCheckbox.isChecked) {
            score += skillPoints * 2
        }
        if (isTeamPlayerCheckbox.isChecked) {
            score += skillPoints
        }
        if (canTravelCheckbox.isChecked) {
            score += skillPoints
        }

        return score
    }

    private fun checkQuestion(radioGroup: RadioGroup, optionAId: Int, optionBId: Int, optionCId: Int, correctAnswer: String?): Int {
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            val selectedAnswer = when (checkedRadioButtonId) {
                optionAId -> "A"
                optionBId -> "B"
                optionCId -> "C"
                else -> ""
            }
            if (selectedAnswer == correctAnswer) {
                return questionPoints
            }
        }
        return 0
    }
}