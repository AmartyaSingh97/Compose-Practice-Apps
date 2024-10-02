package com.amartyasingh.composecalculator

sealed class CalculatorOperations(val symbol: String) {
    data object Add: CalculatorOperations("+")
    data object Subtract: CalculatorOperations("-")
    data object Multiply: CalculatorOperations("x")
    data object Divide: CalculatorOperations("/")
}