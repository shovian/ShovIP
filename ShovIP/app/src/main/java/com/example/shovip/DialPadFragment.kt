package com.example.shovip

import android.content.Context
import android.graphics.Color
import android.net.sip.SipAudioCall
import android.net.sip.SipManager
import android.net.sip.SipProfile
import android.net.sip.SipRegistrationListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
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
                if ( !sharedPreferences.getString("USERNAME", "").isNullOrEmpty() &&
                     !sharedPreferences.getString("DOMAIN", "").isNullOrEmpty()) {
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
        val mainActivity = activity as MainActivity
        with(binding){
            buttonFirst.setOnClickListener {
                var num : String?
                with(binding){num = number.text.toString()}
                val success = mainActivity.dial(num ?: "")

                if ( success ) {
                    findNavController().navigate(R.id.action_DialPadFragment_to_VoiceCallFragment)
                }
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
            binding.indicatorLight.setColorFilter(Color.RED)
        }


        mainActivity.reloadSipProfile()

        mainActivity.sipProfile?.let{
            Log.v("ShovIP", "Setting RegistrationListener...")
            mainActivity.sipManager?.setRegistrationListener(mainActivity.sipProfile?.uriString, object : SipRegistrationListener {

                override fun onRegistering(localProfileUri: String) {
                    Log.v("ShovIP", "onRegistering()")
                    binding.indicatorLight.setColorFilter(Color.BLUE)
                }

                override fun onRegistrationDone(localProfileUri: String, expiryTime: Long) {
                    Log.v("ShovIP", "onRegistrationDone()")
                    binding.indicatorLight.setColorFilter(Color.GREEN)
                }

                override fun onRegistrationFailed(
                    localProfileUri: String,
                    errorCode: Int,
                    errorMessage: String
                ) {
                    Log.v("ShovIP", "onRegistrationFailed() errorCode = $errorCode, errorMessage = $errorMessage")
                    binding.indicatorLight.setColorFilter(Color.RED)
                    activity?.let{
                        Toast.makeText(it,"Registration failed. errorCode = $errorCode, errorMessage = $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
