package com.amartyasingh.composecalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(actions: CalculatorActions){

        when(actions){
            is CalculatorActions.Number -> enterNumber(actions.number)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Operation -> enterOperation(actions.operations)
            is CalculatorActions.Calculate -> performCalculation()
            is CalculatorActions.Delete ->  performDelete()

        }

    }

    private fun performDelete() {
          when{
              state.secondNumber.isNotBlank() -> state = state.copy(secondNumber = state.secondNumber.dropLast(1))
              state.operation != null -> state = state.copy(operation = null)
              state.firstNumber.isNotBlank() -> state = state.copy(firstNumber = state.firstNumber.dropLast(1))
          }
    }

    private fun performCalculation() {
         val number1 = state.firstNumber.toDoubleOrNull()
         val number2 = state.secondNumber.toDoubleOrNull()

        if(number2!=null && number1!=null){
            val result = when(state.operation){
                is CalculatorOperations.Add -> number1 + number2
                is CalculatorOperations.Subtract -> number1 - number2
                is CalculatorOperations.Multiply -> number1 * number2
                is CalculatorOperations.Divide -> number1 / number2
                null -> return
            }

            state = state.copy(firstNumber = result.toString().take(15),
                secondNumber = "",
                operation = null
                )
        }
    }


    private fun enterOperation(operations: CalculatorOperations) {
        if(state.firstNumber.isNotBlank()){
            state = state.copy(operation = operations)
        }

    }

    private fun enterDecimal() {
         if(state.operation==null && !state.firstNumber.contains(".") && state.firstNumber.isNotBlank()){
             state = state.copy(firstNumber = state.firstNumber + ".")
             return
         }
        if(!state.secondNumber.contains(".") && state.secondNumber.isNotBlank()){
            state = state.copy(secondNumber = state.secondNumber + ".")
        }

    }

    private fun enterNumber(number: Int) {
        if(state.operation==null){
             if(state.firstNumber.length >= MAX_NUM_LENGTH){
                 return
             }
            state = state.copy(firstNumber = state.firstNumber + number)
            return
        }
        if(state.secondNumber.length >= MAX_NUM_LENGTH){
            return
        }
        state = state.copy(secondNumber = state.secondNumber + number)
    }

    companion object{
        private const val MAX_NUM_LENGTH = 8
    }


}