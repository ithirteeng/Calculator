package ru.gulevskii.calculator

class CalculatorLogic {
    private var numbers: Array<Double?> = arrayOf(null, null)
    private var lastAction = ""
    private var canInputNumberFlag = false
    private var canInputDotFlag = false
    private var isMadeOperationAction = false
    private var isMadeAction = false

    fun addNumber(currentText: String, buttonText: String): String {
        var inputString = currentText
        isMadeOperationAction = false
        if (!canInputNumberFlag) {
            if (isMadeAction) {
                inputString = ""
                canInputNumberFlag = true
            }
        }
        when (buttonText) {
            "0" -> {
                if (inputString == "") {
                    inputString += "0"
                } else {
                    if (!(inputString[0] == '0' && inputString.length == 1)) {
                        inputString += "0"
                    }
                }
            }
            "1" -> inputString += "1"
            "2" -> inputString += "2"
            "3" -> inputString += "3"
            "4" -> inputString += "4"
            "5" -> inputString += "5"
            "6" -> inputString += "6"
            "7" -> inputString += "7"
            "8" -> inputString += "8"
            "9" -> inputString += "9"
            "," -> {
                if (inputString != "" && !canInputDotFlag) {
                    inputString += "."
                    canInputDotFlag = true
                }
            }
            "ac" -> {
                numbers = arrayOf(null, null)
                lastAction = ""
                inputString = ""
                isMadeAction = false
                canInputDotFlag = false
            }
        }
        return inputString
    }

    fun makeActions(currentText: String, buttonText: String): String {
        canInputDotFlag = false
        var inputString = currentText
        if (buttonText != "=" )
        if (inputString != "") {
            if (!isMadeOperationAction) {
                when (buttonText) {
                    "+" -> {
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
                    "-" -> {
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
                    "÷" -> {
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
                    "x" -> {
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
                when (buttonText) {
                    "+" -> lastAction = "+"
                    "-" -> lastAction = "-"
                    "x" -> lastAction = "*"
                    "÷" -> lastAction = "/"
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
        canInputDotFlag = true
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
        if (number - number.toInt() == 0.0) {
            return number.toInt().toString()
        }
        var tempString = number.toBigDecimal().toPlainString()
        tempString = toExponentialForm(tempString)
        return tempString
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

    private fun toExponentialForm(inputString: String): String {
        var isNegative = false
        var outputString: String = inputString
        if (outputString.length <= 10) {
            return outputString
        }

        if (inputString[0] == '-') isNegative = true
        if (isNegative) outputString = outputString.replace("-", "")
        var number = outputString.toDouble()

        var degreeCounter = 0
        if (number < 1) {
            var s = outputString
            s = s.replace(".", "")
            var c = outputString[0]
            while (c == '0') {
                degreeCounter++
                c = s[degreeCounter]
            }
            if (degreeCounter >= 10) {
                outputString = s.substring(degreeCounter until s.length)
                outputString = outputString.replaceRange(1, 1, ".")
            }
        } else {
            if ("." in outputString) {
                while (outputString[degreeCounter] != '.') {
                    degreeCounter++
                }
                if (degreeCounter >= 10) {
                    outputString = outputString.replace(".", "")
                    degreeCounter--
                }
            } else {
                degreeCounter = outputString.length - 1
            }
            if (degreeCounter >= 10) {
                outputString = outputString.replaceRange(1, 1, ".")
            }
        }

        if (degreeCounter >= 10) {
            if (number >= 1) {
                if (isNegative) {
                    outputString = roundUp(outputString, 6)

                    outputString += "E$degreeCounter"
                    outputString = "-$outputString"
                } else {
                    outputString = roundUp(outputString, 7)
                    outputString += "E$degreeCounter"
                }
            } else {
                if (isNegative) {
                    outputString = roundUp(outputString, 5)

                    outputString += "E-$degreeCounter"
                    outputString = "-$outputString"
                } else {
                    outputString = roundUp(outputString, 6)

                    outputString += "E-$degreeCounter"
                }
            }
        } else {
            if (isNegative) {
                outputString = roundUp(outputString, 9)
                outputString = "-$outputString"
                return outputString
            } else {
                outputString = roundUp(outputString, 10)
                return outputString
            }
        }



        return outputString
    }

    private fun roundUp(number: String, lastIndex: Int): String {
        var flag = false

        if (number[lastIndex] > '5') {
            flag = true
        }
        var tempString = number
        tempString = tempString.replaceRange(lastIndex until tempString.length, "")

        if (flag) {
            var numberLength = tempString.length - 1
            if (tempString[numberLength] == '9') {
                while (tempString[numberLength] == '9') {

                    tempString = tempString.replaceRange(numberLength, numberLength + 1, "0")
                    numberLength--
                }
                if (tempString[numberLength] == '.') {
                    numberLength--
                }
                tempString = tempString.replaceRange(numberLength, numberLength + 1, (tempString[numberLength] + 1).toString())
            } else {
                tempString = tempString.replaceRange(numberLength, numberLength + 1, (tempString[numberLength] + 1).toString())
            }
        }
        return tempString
    }



}