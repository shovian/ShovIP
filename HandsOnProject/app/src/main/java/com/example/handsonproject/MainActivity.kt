package com.example.handsonproject


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.handsonproject.databinding.ActivityMainBinding
import com.example.handsonproject.databinding.ActivityMainBinding.inflate

enum class Operation{
    PLUS,MINUS,MULTIPLY,DIVIDE,MAIN_STATUS
}
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var operation : Operation = Operation.MAIN_STATUS

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        with(binding){
            stash.text=""
        }
    }


    fun onTapB1(view: View){
        with(binding) { input.setText(input.getText().toString() + "1") }
    }
    fun onTapB2(view: View){
        with(binding) { input.setText(input.getText().toString() + "2") }
    }
    fun onTapB3(view: View){
        with(binding) { input.setText(input.getText().toString() + "3") }
    }
    fun onTapB4(view: View){
        with(binding) { input.setText(input.getText().toString() + "4") }
    }
    fun onTapB5(view: View){
        with(binding) { input.setText(input.getText().toString() + "5") }
    }
    fun onTapB6(view: View){
        with(binding) { input.setText(input.getText().toString() + "6") }
    }
    fun onTapB7(view: View){
        with(binding) { input.setText(input.getText().toString() + "7") }
    }
    fun onTapB8(view: View){
        with(binding) { input.setText(input.getText().toString() + "8") }
    }
    fun onTapB9(view: View){
        with(binding) { input.setText(input.getText().toString() + "9") }
    }
    fun onTapB0(view: View){
        with(binding) { input.setText(input.getText().toString() + "0") }
    }
    fun onTapBEqual(view: View){
        with(binding){
            if (input.text.toString() == "" && stash.text.toString() ==  "" ) return
            when(operation){
                Operation.PLUS -> {
                    val res=input.text.toString().toDouble()+stash.text.toString().toDouble()
                    input.setText(res.toString())
                    stash.setText("")
                }
                Operation.MINUS -> {
                    val res=stash.text.toString().toDouble()-input.text.toString().toDouble()
                    input.setText(res.toString())
                    stash.setText("")
                }
                Operation.MULTIPLY -> {
                    val res=input.text.toString().toDouble()*stash.text.toString().toDouble()
                    input.setText(res.toString())
                    stash.setText("")
                }
                Operation.DIVIDE -> {
                    val divisor = input.text.toString().toDouble()
                    val res=stash.text.toString().toDouble()/divisor
                    if(divisor!=0.0) input.setText(res.toString())
                    else {
                        AlertDialog
                            .Builder(this@MainActivity)
                            .setTitle("Err")
                            .setMessage("Division by zero is not allowed!")
                            .show()
                    }
                    stash.setText("")
                }
            }
            operation=Operation.MAIN_STATUS
        }
    }
    fun onTapBPlus(view: View){
        with(binding) {
            if (input.text.toString() ==  "" ) return
            if (operation==Operation.MAIN_STATUS) {
                stash.setText(input.getText().toString())
                operation=Operation.PLUS
            }
            else {
                onTapBEqual(view)
                onTapBPlus(view)
            }
            input.setText("")
        }
    }
    fun onTapBMinus(view: View){
        with(binding) {
            if (input.text.toString() ==  "" ) return
            if (operation==Operation.MAIN_STATUS) {
                stash.setText(input.getText().toString())
                operation=Operation.MINUS
            }
            else {
                onTapBEqual(view)
                onTapBMinus(view)
            }
            input.setText("")
        }
    }
    fun onTapBMultiply(view: View){
        with(binding) {
            if (input.text.toString() ==  "" ) return
            if (operation==Operation.MAIN_STATUS) {
                stash.setText(input.getText().toString())
                operation=Operation.MULTIPLY
            }
            else {
                onTapBEqual(view)
                onTapBMultiply(view)
            }
            input.setText("")
        }
    }
    fun onTapBDivide(view: View){
        with(binding) {
            if (input.text.toString() ==  "" ) return
            if (operation==Operation.MAIN_STATUS) {
                stash.setText(input.getText().toString())
                operation=Operation.DIVIDE
            }
            else {
                onTapBEqual(view)
                onTapBDivide(view)
            }
            input.setText("")
        }
    }
    fun onTapBClear(view: View){
        with(binding) {
            stash.setText("")
            operation=Operation.MAIN_STATUS
            input.setText("")
        }
    }
}