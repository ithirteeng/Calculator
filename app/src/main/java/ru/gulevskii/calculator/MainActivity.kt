package ru.gulevskii.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import ru.gulevskii.calculator.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var lastAction = ""
    private val logic = CalculatorLogic()
    private var isMadeAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        numbersButtonClick()
        mainActionsButtonsClick()
        percentButtonClick()
        negativeButtonClick()
        equalButtonClick()
    }

    private fun numbersButtonClick() {
        for (button in binding.numberButtonsGroup.referencedIds) {
            findViewById<View>(button).setOnClickListener {
                var inputString = binding.screenText.text.toString()

                if (inputString.length >= 10 && !isMadeAction) {
                    if (button == R.id.acButton) {
                        inputString =
                            logic.addNumber(inputString, binding.acButton.text.toString())
                        lastAction = ""
                        binding.currentOperatorViewer.text = ""
                    } else {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.toast_max_elements),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    isMadeAction = false
                    when (button) {
                        R.id.zeroButton -> {
                            if (inputString != "" && inputString[0] == '0' && inputString.length == 1) {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.toast_impossibility),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            inputString =
                                logic.addNumber(inputString, binding.zeroButton.text.toString())
                        }
                        R.id.oneButton ->
                            inputString =
                                logic.addNumber(inputString, binding.oneButton.text.toString())
                        R.id.twoButton ->
                            inputString =
                                logic.addNumber(inputString, binding.twoButton.text.toString())
                        R.id.threeButton ->
                            inputString =
                                logic.addNumber(inputString, binding.threeButton.text.toString())
                        R.id.fourButton ->
                            inputString =
                                logic.addNumber(inputString, binding.fourButton.text.toString())
                        R.id.fiveButton ->
                            inputString =
                                logic.addNumber(inputString, binding.fiveButton.text.toString())
                        R.id.sixButton ->
                            inputString =
                                logic.addNumber(inputString, binding.sixButton.text.toString())
                        R.id.sevenButton ->
                            inputString =
                                logic.addNumber(inputString, binding.sevenButton.text.toString())
                        R.id.eightButton ->
                            inputString =
                                logic.addNumber(inputString, binding.eightButton.text.toString())
                        R.id.nineButton ->
                            inputString =
                                logic.addNumber(inputString, binding.nineButton.text.toString())
                        R.id.commaButton -> {
                            if ((inputString == "" || "." in inputString || isMadeAction)) {
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.toast_impossibility),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                inputString = logic.addNumber(
                                    inputString,
                                    binding.commaButton.text.toString()
                                )
                            }

                        }
                        R.id.acButton -> {
                            inputString =
                                logic.addNumber(inputString, binding.acButton.text.toString())
                            lastAction = ""
                            binding.currentOperatorViewer.text = ""
                        }
                    }

                }
                binding.screenText.text = inputString
            }

        }


    }

    private fun mainActionsButtonsClick() {
        for (button in binding.mainActionsGroup.referencedIds) {

            findViewById<View>(button).setOnClickListener {
                var inputString = binding.screenText.text.toString()
                binding.screenText.text = ""
                isMadeAction = true

                if (inputString == "") {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.toast_input_number),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when (it.id) {
                        R.id.plusButton -> {
                            inputString =
                                logic.makeActions(inputString, binding.plusButton.text.toString())
                            lastAction = "+"
                            binding.screenText.text = inputString
                        }
                        R.id.minusButton -> {
                            inputString =
                                logic.makeActions(inputString, binding.minusButton.text.toString())
                            lastAction = "-"
                            binding.screenText.text = inputString
                        }
                        R.id.divButton -> {
                            inputString =
                                logic.makeActions(inputString, binding.divButton.text.toString())
                            lastAction = "/"
                            binding.screenText.text = inputString
                        }
                        R.id.multiplyButton -> {
                            inputString = logic.makeActions(
                                inputString,
                                binding.multiplyButton.text.toString()
                            )
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
            inputString = logic.makePercentAction(inputString)

            if (inputString == "" || inputString == binding.screenText.text.toString()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.toast_input_number),
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

            if (inputString == "") {
                Toast.makeText(
                    this,
                    resources.getString(R.string.toast_input_number),
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.screenText.text = logic.makeNegativeAction(inputString)
        }
    }

    private fun equalButtonClick() {
        binding.equalButton.setOnClickListener {
            var inputString: String = binding.screenText.text.toString()

            if (inputString == "") {
                Toast.makeText(
                    this,
                    resources.getString(R.string.toast_input_number),
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




}

