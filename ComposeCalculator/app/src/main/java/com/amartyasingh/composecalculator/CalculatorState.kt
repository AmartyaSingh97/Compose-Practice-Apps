package com.amartyasingh.composecalculator

data class CalculatorState(
    val firstNumber: String = "",
    val operation: CalculatorOperations? = null,
    val secondNumber: String = ""
)
