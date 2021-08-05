package com.example.shovip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentSettingsBinding
import com.example.shovip.databinding.FragmentVideoCallBinding

class VideoCallFragment : Fragment() {
    private var binding by autoCleared<FragmentVideoCallBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVideoCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_VideoCallFragment_to_VoiceCallFragment)
        }
        binding.bHangup.setOnClickListener {
            findNavController().navigate(R.id.action_VideoCallFragment_to_DialPadFragment)
        }
    }
}
