package ru.gulevskii.calculator

import java.text.DecimalFormat

class CalculatorLogic() {
    private var numbers: Array<Double?> = arrayOf(null, null)
    private var lastAction = ""
    private var canInputNumberFlag = false
    private var canInputDotFlag = false
    private var isMadeOperationAction = false
    private var isMadeAction = false

    fun addNumber(currentText: String, buttonId: Int): String {
        var inputString = currentText
        isMadeOperationAction = false
        if (!canInputNumberFlag) {
            if (isMadeAction) {
                inputString = ""
                canInputNumberFlag = true
            }
        }
        when (buttonId) {
            R.id.zeroButton -> {
                if (inputString == "") {
                    inputString += "0"
                } else {
                    if (!(inputString[0] == '0' && inputString.length == 1)) {
                        inputString += "0"
                    }
                }
            }
            R.id.oneButton -> inputString += "1"
            R.id.twoButton -> inputString += "2"
            R.id.threeButton -> inputString += "3"
            R.id.fourButton -> inputString += "4"
            R.id.fiveButton -> inputString += "5"
            R.id.sixButton -> inputString += "6"
            R.id.sevenButton -> inputString += "7"
            R.id.eightButton -> inputString += "8"
            R.id.nineButton -> inputString += "9"
            R.id.commaButton -> {
                if (inputString != "" && !canInputDotFlag) {
                    inputString += "."
                    canInputDotFlag = true
                }
            }
            R.id.acButton -> {
                numbers = arrayOf(null, null)
                lastAction = ""
                inputString = ""
                isMadeAction = false
                canInputDotFlag = false
            }
        }
        return inputString
    }

    fun makeActions(currentText: String, buttonId: Int): String {
        var inputString = currentText
        if (buttonId != R.id.equalButton )
        if (inputString != "") {
            if (!isMadeOperationAction) {
                when (buttonId) {
                    R.id.plusButton -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "+"
                        inputString = printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.minusButton -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "-"
                        inputString = printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.divButton -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "/"
                        inputString = printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                    R.id.multiplyButton -> {
                        if (!isMadeAction) {
                            numbers[0] = inputString.toDouble()
                            isMadeAction = true
                        } else {
                            numbers[1] = inputString.toDouble()
                            solve()
                        }
                        lastAction = "*"
                        inputString = printNumbers(numbers[0]!!)
                        canInputNumberFlag = false
                    }
                }
            } else {
                when (buttonId) {
                    R.id.plusButton -> lastAction = "+"
                    R.id.minusButton -> lastAction = "-"
                    R.id.multiplyButton -> lastAction = "*"
                    R.id.divButton -> lastAction = "/"
                }
            }
        }
        isMadeOperationAction = true
        return inputString
    }

    fun makeEqualAction(currentText: String): String {
        var inputString = currentText
        if (lastAction != "") {
            if (!isMadeOperationAction) {
                if (isMadeAction) {
                    numbers[1] = inputString.toDouble()
                    solve()
                    inputString = numbers[0].toString()
                }
            }
        }

        if (inputString != "") {
            numbers[0] = inputString.toDouble()
            inputString = printNumbers(numbers[0]!!)
        }
        canInputNumberFlag = false
        isMadeAction = false
        isMadeOperationAction = false
        lastAction = ""
        numbers = arrayOf(null, null)
        return inputString
    }

    fun makeNegativeAction(currentText: String): String {
        var inputString = currentText

        if (inputString != "") {
            if (!isMadeOperationAction) {
                inputString = if (inputString.toDouble() < 0.0) {
                    inputString.replace("-", "")
                } else {
                    "-$inputString"
                }
            } else {
                numbers[0] = -numbers[0]!!
                inputString = printNumbers(numbers[0]!!)
            }
        }

        return inputString
    }

    fun makePercentAction(currentText: String): String {
        var inputString = currentText
        if (!(inputString == "" || numbers[0] == null)) {
            numbers[1] = numbers[0]!! / 100.0 * inputString.toDouble()
            solve()
            inputString = printNumbers(numbers[0]!!)
            isMadeOperationAction = true
            canInputNumberFlag = false
        }
        return inputString
    }

    private fun printNumbers(number: Double): String {
        val dForm = DecimalFormat("0.00000E00")
        var string = number.toString()
        return if (string.length <= 10) {
            if (number - number.toInt() == 0.0) {
                val temp: Int = number.toInt()
                temp.toString()
            } else {
                number.toString()
            }
        } else {
            string = dForm.format(number)
            string = string.replaceFirst(',', '.')
            string
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

}