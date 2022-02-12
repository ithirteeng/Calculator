package ru.gulevskii.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var numbers: Array<Double?> = arrayOf(null, null)
    private var lastAction = ""
    var canInputNumberFlag = false
    private var canInputDotFlag = false
    var isMadeOperationAction = false
    private var isMadeAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun numbersEvent(view: View) {
        var inputString = screenText.text.toString()
        isMadeOperationAction = false

        val bufferButton = view as ImageView
        makeClick(bufferButton)

        if (!canInputNumberFlag) {
            if (isMadeAction) {
                screenText.text = ""
                inputString = ""
                canInputNumberFlag = true
            }
        }
        when (bufferButton.id) {
            R.id.zero_button -> {
                if (inputString == "") {
                    inputString += "0"
                } else {
                    if (!(inputString[0] == '0' && inputString.length == 1)) {
                        inputString += "0"
                    } else {
                        Toast.makeText(
                            this,
                            "you can't do it!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            R.id.one_button -> inputString += "1"
            R.id.two_button -> inputString += "2"
            R.id.three_button -> inputString += "3"
            R.id.four_button -> inputString += "4"
            R.id.five_button -> inputString += "5"
            R.id.six_button -> inputString += "6"
            R.id.seven_button -> inputString += "7"
            R.id.eight_button -> inputString += "8"
            R.id.nine_button -> inputString += "9"
            R.id.comma_button -> {
                if (inputString != "" && !canInputDotFlag) {
                    inputString += "."
                    canInputDotFlag = true
                } else {
                    Toast.makeText(
                        this,
                        "you can't do it!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.ac_button -> {
                numbers = arrayOf(null, null)
                lastAction = ""
                currentOperatorViewer.text = ""
                inputString = ""
                isMadeAction = false
                canInputDotFlag = false
            }
        }
        screenText.text = inputString

    }

    fun actionsEvents(view: View) {
        canInputDotFlag = false
        var inputString = screenText.text.toString()
        screenText.text = ""

        val bufferButton = view as ImageView
        makeClick(bufferButton)

        if (inputString == "") {
            Toast.makeText(
                this,
                "please, input a number",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (!isMadeOperationAction) {
                when (bufferButton.id) {
                    R.id.plus_button -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "+"
                        printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.minus_button -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "-"
                        printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.div_button -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "/"
                        printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.multiply_button -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "*"
                        printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                }
            } else {
                when (bufferButton.id) {
                    R.id.plus_button -> lastAction = "+"
                    R.id.minus_button -> lastAction = "-"
                    R.id.multiply_button -> lastAction = "*"
                    R.id.div_button -> lastAction = "/"
                }
                screenText.text = inputString
            }

            currentOperatorViewer.text = lastAction
        }
        isMadeOperationAction = true
    }

    fun percentActionEvent(view: View) {
        var inputString = screenText.text.toString()

        view as ImageView
        makeClick(view)

        if (inputString == "" || numbers[0] == null) {
            Toast.makeText(
                this,
                "Please, input the number",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            currentOperatorViewer.text = "%"
            numbers[1] = numbers[0]!! / 100.0 * inputString.toDouble()
            solve()
            printNumbers(numbers[0]!!)
            isMadeOperationAction = true
            canInputNumberFlag = false
        }
    }

    fun makeNegativeEvent(view: View) {
        var inputString = screenText.text.toString()

        view as ImageView
        makeClick(view)

        if (inputString == "") {
            Toast.makeText(
                this,
                "please, input a number",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (!isMadeOperationAction) {
                inputString = if (inputString.toDouble() < 0.0) {
                    inputString.replace("-", "")
                } else {
                    "-$inputString"
                }
                screenText.text = inputString
            } else {
                numbers[0] = -numbers[0]!!
                printNumbers(numbers[0]!!)
            }
        }

    }

    fun equalActionEvent(view: View) {
        var current: String = screenText.text.toString()
        if (lastAction != "") {
            if (!isMadeOperationAction) {
                if (isMadeAction) {
                    numbers[1] = current.toDouble()
                    solve()
                    current = numbers[0].toString()
                }
            }
        }
        view as ImageView
        makeClick(view)

        if (current == "") {
            Toast.makeText(
                this,
                "please, input a number",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            numbers[0] = current.toDouble()
            printNumbers(numbers[0]!!)
        }
        canInputNumberFlag = false
        isMadeAction = false
        isMadeOperationAction = false
        currentOperatorViewer.text = "="
        lastAction = ""
        numbers = arrayOf(null, null)

    }

    private fun printNumbers(number: Double) {
        if (number - number.toInt() == 0.0) {
            val temp: Int = number.toInt()
            screenText.text = temp.toString()
        } else {
            screenText.text = number.toString()
        }
    }

    private fun solve() {
        when (lastAction) {
            "*" -> numbers[0] = numbers[0]!! * numbers[1]!!
            "+" -> numbers[0] = numbers[0]!! + numbers[1]!!
            "-" -> numbers[0] = numbers[0]!! - numbers[1]!!
            "/" -> numbers[0] = numbers[0]!! / numbers[1]!!
        }
        numbers[1] = null
    }

    private fun makeClick(view: View) {
        view.setBackgroundColor(resources.getColor(R.color.clickColor, null))
        Handler().postDelayed({
            kotlin.run { view.setBackgroundColor(Color.TRANSPARENT) }
        }, 50)
    }

}

