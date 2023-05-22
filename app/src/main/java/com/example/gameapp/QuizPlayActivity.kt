package com.example.gameapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gameapp.databinding.PlayQuizActivityBinding

class QuizPlayActivity : AppCompatActivity() {

    private lateinit var binding: PlayQuizActivityBinding
    private var correctAnswerIndex: Int? = null  // stores the index of the correct answer
    private var score = 0 // stores the score of the game
    private var quizQuestionCount = 1 // represents the question number on screen
    private var correctAnswer: String? = null // stores the correct answer text
    private var selectedAnswerIndex: Int? = null // stores the index of the answer selected by the user
    private var currentQuestionNumber = 0 // store the index of the current question on screen
    private var fourQuestionSet: List<List<Any>>? = null // set of 4 random questions
    private var questionCount = 4 // total questions that would be shown to user

    // array of colors to set for the background of each quiz question
    private val colors = intArrayOf(
        R.color.light_color_1,
        R.color.light_color_2,
        R.color.light_color_3,
        R.color.light_color_4,
        R.color.light_color_5,
        R.color.light_color_6,
        R.color.light_color_7,
        R.color.light_color_8,
        R.color.light_color_9,
        R.color.light_color_10
    )

    // array of the trivia questions
    private val triviaQuestions = arrayOf(
        "What was the first feature-length animated film released by Disney?",
        "Who won the Best Actor Academy Award in 2020?",
        "Who wrote the novel To Kill a Mockingbird?",
        "What is the capital of France?",
        "Who is the current U.S. President?",
        "What type of food is pizza?",
        "What is the name of the first person to walk on the moon?",
        "Who directed the movie The Shawshank Redemption?",
        "What is the largest ocean in the world?",
        "Who wrote the Harry Potter series of books?",
        "What is the name of the highest mountain peak in the world?",
        "Who played the lead role in the movie The Silence of the Lambs?",
        "What is the currency used in Japan?",
        "Who won the Best Picture Academy Award in 2022?",
        "Who was the lead singer of the Beatles?",
        "What is the capital of India?",
        "Who played the character of Indiana Jones in the movie franchise of the same name?",
        "What is the name of the world's longest river?",
        "Who directed the movie The Godfather?",
        "What is the name of the famous painting by Leonardo da Vinci that depicts the last supper?"
    )
    // List of lists of options for each question
    private val triviaChoices = listOf(
        listOf("Fantasia", "Snow White and the Seven Dwarfs", "Dumbo", "Bambi"),
        listOf("Adam Driver", "Joaquin Phoenix", "Jonathan Pryce", "Antonio Banderas"),
        listOf("Kathryn Stockett", "Harper Lee", "Gillian Flynn", "Sue Monk Kidd"),
        listOf("Marseille", "Paris", "Lyon", "Nice"),
        listOf("Donald Trump", "Joe Biden", "Barack Obama", "George W. Bush"),
        listOf("Italian", "Mexican", "Chinese", "Indian"),
        listOf("Buzz Aldrin", "Neil Armstrong", "Michael Collins", "Alan Shepard"),
        listOf("Martin Scorsese", "Quentin Tarantino", "Frank Darabont", "Christopher Nolan"),
        listOf("Indian Ocean", "Atlantic Ocean", "Pacific Ocean", "Arctic Ocean"),
        listOf("Stephenie Meyer", "J.K. Rowling", "Suzanne Collins", "Veronica Roth"),
        listOf("K2", "Mount Everest", "Lhotse", "Makalu"),
        listOf("Julianne Moore", "Jodie Foster", "Meryl Streep", "Natalie Portman"),
        listOf("Won", "Yen", "Dollar", "Euro"),
        listOf("The Trial of the Chicago 7", "Nomadland", "Minari", "Promising Young Woman"),
        listOf("Paul McCartney", "John Lennon", "George Harrison", "Ringo Starr"),
        listOf("Mumbai", "Delhi", "Bangalore", "Chennai"),
        listOf("Tom Cruise", "Harrison Ford", "Brad Pitt", "Matt Damon"),
        listOf("Amazon River", "Nile River", "Yangtze River", "Yellow River"),
        listOf("Steven Spielberg", "Francis Ford Coppola", "Martin Scorsese", "Christopher Nolan"),
        listOf("Mona Lisa", "The Last Supper", "The Vitruvian Man", "The Baptism of Christ")
    )
    // list of indexes of the correct answer
    private val triviaAnswers = arrayOf(1, 1, 1, 1, 1, 0, 1, 0, 2, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlayQuizActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // the randomQuiz function returns a set of 4 random questions
        fourQuestionSet = randomQuiz()
        // it shows the 4 questions to the user
        showQuestion(fourQuestionSet!!, currentQuestionNumber)

        // change the color of the button that is selected by the user
        binding.optionBtn1.setOnClickListener {
            binding.optionBtn1.background = ContextCompat.getDrawable(this, R.drawable.btn_selected)
            binding.optionBtn2.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn3.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn4.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            selectedAnswerIndex = 0
        }
        binding.optionBtn2.setOnClickListener {
            binding.optionBtn1.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn2.background = ContextCompat.getDrawable(this, R.drawable.btn_selected)
            binding.optionBtn3.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn4.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            selectedAnswerIndex = 1
        }
        binding.optionBtn3.setOnClickListener {
            binding.optionBtn1.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn2.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn3.background = ContextCompat.getDrawable(this, R.drawable.btn_selected)
            binding.optionBtn4.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            selectedAnswerIndex = 2
        }
        binding.optionBtn4.setOnClickListener {
            binding.optionBtn1.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn2.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn3.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            binding.optionBtn4.background = ContextCompat.getDrawable(this, R.drawable.btn_selected)
            selectedAnswerIndex = 3
        }

        binding.submitAnswerBtn.setOnClickListener {
            checkAnswer(selectedAnswerIndex, correctAnswerIndex)
        }

    }

    // randomly selects 4 questions, their corresponding options and the correct answer index
    private fun randomQuiz(): List<List<Any>> {
        val random = java.util.Random()
        val indices = mutableSetOf<Int>()
        while (indices.size < 4) {
            indices.add(random.nextInt(triviaChoices.size))
        }
        // creates a set of 4 questions
        val quiz = mutableListOf<List<Any>>()
        for (index in indices) {
            val question: String = triviaQuestions[index]
            val choices: List<String> = triviaChoices[index]
            val answer: Int = triviaAnswers[index]
            quiz.add(listOf(question, choices, answer))
        }
        return quiz
    }


    private fun showQuestion(fourQuestionSet: List<List<Any>>, currentQuestionNumber: Int) {
        // set random background color
        val randomColor = getRandomColor()
        binding.quizLayout.setBackgroundColor(ContextCompat.getColor(this, randomColor))

        // question number
        binding.questionCount.text = getString(R.string.questionCounter, quizQuestionCount)

        // pick one question
        val quizQuestion = fourQuestionSet[currentQuestionNumber]
        binding.questionText.text = quizQuestion[0].toString()

        // set options
        val quizOptions = quizQuestion[1] as List<String>
        binding.optionBtn1.text = quizOptions[0]
        binding.optionBtn2.text = quizOptions[1]
        binding.optionBtn3.text = quizOptions[2]
        binding.optionBtn4.text = quizOptions[3]

        // correct answer
        correctAnswerIndex = quizQuestion[2] as Int
        correctAnswer = quizOptions[correctAnswerIndex!!]
    }

    // checks if the user gave correct answer and increments score
    private fun checkAnswer(selectedAnswerIndex: Int?, correctAnswerIndex: Int?) {
        var submitButton = "Next"
        if(currentQuestionNumber == 3){
            submitButton = "Finish"
        }
        var alertTitle: String = if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            "Correct Answer!"
        } else {
            "Wrong Answer!"
        }
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Your Score: $score \nAnswer: $correctAnswer")
            .setPositiveButton(submitButton) {
                    _, _ -> checkQuizCount(selectedAnswerIndex)
            }
            .setCancelable(false)
            .show()
    }

    private fun checkQuizCount(selectedAnswerIndex: Int?) {
        if (quizQuestionCount == questionCount) {
            // show final score and storing the score in cache so that it persists even after app is exited
            val sharedPref = this?.getSharedPreferences("userLastScore", Context.MODE_PRIVATE)
            if (sharedPref != null) {
                with (sharedPref.edit()) {
                    putInt("lastScore", score)
                    apply()
                }
            }
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        } else {
            quizQuestionCount++
            currentQuestionNumber++
            showQuestion(fourQuestionSet!!, currentQuestionNumber)

            // reset the color of the button that was selected in previous question
            when(selectedAnswerIndex) {
                0 -> binding.optionBtn1.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
                1 -> binding.optionBtn2.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
                2 -> binding.optionBtn3.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
                3 -> binding.optionBtn4.background = ContextCompat.getDrawable(this, R.drawable.normal_btn)
            }
        }
    }

    // select a random color for background of each quiz question
    private fun getRandomColor(): Int {
        val randomIndex = (colors.indices).random()
        return colors[randomIndex]
    }
}

