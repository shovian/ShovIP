package com.example.shovip

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shovip.databinding.FragmentSettingsBinding

class
SettingsFragment : Fragment() {
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
        activity?.let{
            val sharedPreferences = it.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            with(binding) {
                var textSaved = sharedPreferences.getString("USERNAME", "")
                username.setText(textSaved)
                textSaved = sharedPreferences.getString("PASSWORD", "")
                password.setText(textSaved)
                textSaved = sharedPreferences.getString("DOMAIN", "")
                domain.setText(textSaved)
                textSaved = sharedPreferences.getString("PROXY", "")
                proxy.setText(textSaved)
            }
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
            insertedText = proxy.text.toString()
            editor.apply{
                putString("PROXY",insertedText)
            }.apply()
            Toast.makeText(act,"Data Saved",Toast.LENGTH_SHORT).show()

            // Also, reload the SipProfile
            (activity as MainActivity).reloadSipProfile()
        }
    }
}