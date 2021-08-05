package com.example.shovip

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shovip.databinding.FragmentDialPadBinding
import com.example.shovip.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var binding by autoCleared<FragmentSettingsBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        loadData()
        binding.button.setOnClickListener {
            saveData()
        }

    }

    private fun loadData(){
        val act = this.activity
        val sharedPreferences = act!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        with(binding){
            var textSaved = sharedPreferences.getString("USERNAME",null)
            username.setText(textSaved)
            textSaved = sharedPreferences.getString("PASSWORD",null)
            password.setText(textSaved)
            textSaved = sharedPreferences.getString("DOMAIN",null)
            domain.setText(textSaved)
        }
    }

    private fun saveData(){
        val act = this.activity
        with(binding){
            val sharedPreferences : SharedPreferences = act!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            var insertedText : String = username.text.toString()
            editor.apply{
                putString("USERNAME",insertedText)
            }.apply()
            insertedText = password.text.toString()
            editor.apply{
                putString("PASSWORD",insertedText)
            }.apply()
            insertedText = domain.text.toString()
            editor.apply{
                putString("DOMAIN",insertedText)
            }.apply()
            Toast.makeText(act,"Data Saved",Toast.LENGTH_SHORT).show()
        }
    }
}