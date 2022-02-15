package ru.gulevskii.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import ru.gulevskii.calculator.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var lastAction = ""
    private val logic = CalculatorLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.numberButtonsGroup

        clickListeners()
    }

    private fun clickListeners() {
        numbersButtonClick()
        mainActionsButtonsClick()
        percentButtonClick()
        negativeButtonClick()
        equalButtonClick()
    }


    private fun numbersButtonClick() {
        for (button in binding.numberButtonsGroup.referencedIds) {
            findViewById<ImageView>(button).setOnClickListener {
                var inputString = binding.screenText.text.toString()

                makeClick(findViewById(button))
                when (button) {
                    R.id.zeroButton -> {
                        if (inputString != "" && inputString[0] == '0' && inputString.length == 1) {
                            Toast.makeText(
                                this,
                                "you can't do this!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        inputString = logic.addNumber(inputString, R.id.zeroButton)
                    }
                    R.id.oneButton -> inputString = logic.addNumber(inputString, R.id.oneButton)
                    R.id.twoButton -> inputString = logic.addNumber(inputString, R.id.twoButton)
                    R.id.threeButton -> inputString = logic.addNumber(inputString, R.id.threeButton)
                    R.id.fourButton -> inputString = logic.addNumber(inputString, R.id.fourButton)
                    R.id.fiveButton -> inputString = logic.addNumber(inputString, R.id.fiveButton)
                    R.id.sixButton -> inputString = logic.addNumber(inputString, R.id.sixButton)
                    R.id.sevenButton -> inputString = logic.addNumber(inputString, R.id.sevenButton)
                    R.id.eightButton -> inputString = logic.addNumber(inputString, R.id.eightButton)
                    R.id.nineButton -> inputString = logic.addNumber(inputString, R.id.nineButton)
                    R.id.commaButton -> {
                        if (!(inputString != "" && "." !in inputString)) {
                            Toast.makeText(
                                this,
                                "you can't do it!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            inputString = logic.addNumber(inputString, R.id.commaButton)
                        }

                    }
                    R.id.acButton -> {
                        inputString = logic.addNumber(inputString, R.id.acButton)
                        lastAction = ""
                    }
                }
                binding.screenText.text = inputString
            }

        }

    }

    private fun mainActionsButtonsClick() {
        for (button in binding.mainActionsGroup.referencedIds) {
            findViewById<ImageView>(button).setOnClickListener {
                var inputString = binding.screenText.text.toString()
                binding.screenText.text = ""

                makeClick(it)

                if (inputString == "") {
                    Toast.makeText(
                        this,
                        "please, input a number",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when (it.id) {
                        R.id.plusButton -> {
                            inputString = logic.makeActions(inputString, R.id.plusButton)
                            lastAction = "+"
                            binding.screenText.text = inputString
                        }
                        R.id.minusButton -> {
                            inputString = logic.makeActions(inputString, R.id.minusButton)
                            lastAction = "-"
                            binding.screenText.text = inputString
                        }
                        R.id.divButton -> {
                            inputString = logic.makeActions(inputString, R.id.divButton)
                            lastAction = "/"
                            binding.screenText.text = inputString
                        }
                        R.id.multiplyButton -> {
                            inputString = logic.makeActions(inputString, R.id.multiplyButton)
                            lastAction = "*"
                            binding.screenText.text = inputString
                        }
                    }
                    binding.currentOperatorViewer.text = lastAction
                }
            }
        }
    }

    private fun percentButtonClick() {
        binding.percentButton.setOnClickListener {
            var inputString = binding.screenText.text.toString()
            makeClick(it)
            inputString = logic.makePercentAction(inputString)

            if (inputString == "" || inputString == binding.screenText.text.toString()) {
                Toast.makeText(
                    this,
                    "Please, input the number",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.screenText.text = inputString
            binding.currentOperatorViewer.text = "%"

        }
    }

    private fun negativeButtonClick() {
        binding.negativeButton.setOnClickListener {
            val inputString = binding.screenText.text.toString()
            makeClick(it)

            if (inputString == "") {
                Toast.makeText(this,
                    "Please, input a number",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.screenText.text = logic.makeNegativeAction(inputString)
        }
    }

    private fun equalButtonClick() {
        binding.equalButton.setOnClickListener {
            var inputString: String = binding.screenText.text.toString()
            makeClick(it)

            if (inputString == "") {
                Toast.makeText(
                    this,
                    "please, input a number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                inputString = logic.makeEqualAction(inputString)
                 binding.currentOperatorViewer.text = "="
            }

            lastAction = ""
            binding.screenText.text = inputString
        }
    }

    private fun makeClick(view: View) {
        view.setBackgroundColor(resources.getColor(R.color.clickColor, null))
        Handler().postDelayed({
            kotlin.run { view.setBackgroundColor(Color.TRANSPARENT) }
        }, 50)
    }


}

