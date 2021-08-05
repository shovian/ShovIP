package com.example.shovip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentVideoCallBinding
import com.example.shovip.databinding.FragmentVoiceCallBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class VoiceCallFragment : Fragment() {
    private var binding by autoCleared<FragmentVoiceCallBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVoiceCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_VoiceCallFragment_to_VideoCallFragment)
        }
        binding.bHangup.setOnClickListener {
            findNavController().navigate(R.id.action_VoiceCallFragment_to_DialPadFragment)
        }
    }
}
