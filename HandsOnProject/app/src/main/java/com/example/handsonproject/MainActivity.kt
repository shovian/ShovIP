package com.example.handsonproject


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.handsonproject.databinding.ActivityMainBinding
import com.example.handsonproject.databinding.ActivityMainBinding.inflate


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var op : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        with(binding){
            editText1.hint="angka"
            textView.text=""
        }
    }


    fun onTapB1(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "1") }
    }
    fun onTapB2(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "2") }
    }
    fun onTapB3(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "3") }
    }
    fun onTapB4(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "4") }
    }
    fun onTapB5(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "5") }
    }
    fun onTapB6(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "6") }
    }
    fun onTapB7(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "7") }
    }
    fun onTapB8(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "8") }
    }
    fun onTapB9(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "9") }
    }
    fun onTapB0(view: View){
        with(binding) { editText1.setText(editText1.getText().toString() + "0") }
    }
    fun onTapBEqual(view: View){
        with(binding){
            if (editText1.text.toString().equals("")&&textView.text.toString().equals("")) return
            if(op==1){
                val res=editText1.text.toString().toDouble()+textView.text.toString().toDouble()
                editText1.setText(res.toString())
                textView.setText("")
            }
            if(op==2){
                val res=textView.text.toString().toDouble()-editText1.text.toString().toDouble()
                editText1.setText(res.toString())
                textView.setText("")
            }
            if(op==3){
                val res=editText1.text.toString().toDouble()*textView.text.toString().toDouble()
                editText1.setText(res.toString())
                textView.setText("")
            }
            if(op==4){
                val res=textView.text.toString().toDouble()/editText1.text.toString().toDouble()
                editText1.setText(res.toString())
                textView.setText("")
            }
            op=0
        }
    }
    fun onTapBPlus(view: View){
        with(binding) {
            if (op==0) {
                textView.setText(editText1.getText().toString())
                op=1
            }
            else {
                onTapBEqual(view)
                onTapBPlus(view)
            }
            editText1.setText("")
        }
    }
    fun onTapBMinus(view: View){
        with(binding) {
            if (op==0) {
                textView.setText(editText1.getText().toString())
                op=2
            }
            else {
                onTapBEqual(view)
                onTapBMinus(view)
            }
            editText1.setText("")
        }
    }
    fun onTapBMultiply(view: View){
        with(binding) {
            if (op==0) {
                textView.setText(editText1.getText().toString())
                op=3
            }
            else {
                onTapBEqual(view)
                onTapBMultiply(view)
            }
            editText1.setText("")
        }
    }
    fun onTapBDivide(view: View){
        with(binding) {
            if (op==0) {
                textView.setText(editText1.getText().toString())
                op=4
            }
            else {
                onTapBEqual(view)
                onTapBDivide(view)
            }
            editText1.setText("")
        }
    }
    fun onTapBClear(view: View){
        with(binding) {
            textView.setText("")
            op=0
            editText1.setText("")
        }
    }
}