package com.example.shovip

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentDialPadBinding
import android.annotation.SuppressLint as SuppressLint1

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DialPadFragment : Fragment() {
    private var binding by autoCleared<FragmentDialPadBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialPadBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint1("SetTextI18n")
    private fun loadData(){
        activity?.let{
            val sharedPreferences = it.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            with(binding) {
                
                binding.indicatorLight.setColorFilter(Color.GREEN)
                if (sharedPreferences.getString("USERNAME", "").isNullOrEmpty()||
                    sharedPreferences.getString("DOMAIN", "").isNullOrEmpty()) {
                    account.text = sharedPreferences.getString("USERNAME", "") + "@" + sharedPreferences.getString("DOMAIN", "")
                }
                else {
                    binding.indicatorLight.setColorFilter(Color.RED)
                    account.text = getString(R.string.no_account)
                }
            }
        }
    }
    @SuppressLint1("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

        with(binding){
            buttonFirst.setOnClickListener {
                findNavController().navigate(R.id.action_DialPadFragment_to_VoiceCallFragment)
            }
            b1.setOnClickListener{
                number.setText(number.text.toString()+1.toString())
            }
            b2.setOnClickListener{
                number.setText(number.text.toString()+2.toString())
            }
            b3.setOnClickListener{
                number.setText(number.text.toString()+3.toString())
            }
            b4.setOnClickListener{
                number.setText(number.text.toString()+4.toString())
            }
            b5.setOnClickListener{
                number.setText(number.text.toString()+5.toString())
            }
            b6.setOnClickListener{
                number.setText(number.text.toString()+6.toString())
            }
            b7.setOnClickListener{
                number.setText(number.text.toString()+7.toString())
            }
            b8.setOnClickListener{
                number.setText(number.text.toString()+8.toString())
            }
            b9.setOnClickListener{
                number.setText(number.text.toString()+9.toString())
            }
            b0.setOnClickListener{
                number.setText(number.text.toString()+0.toString())
            }
        }
    }
}
