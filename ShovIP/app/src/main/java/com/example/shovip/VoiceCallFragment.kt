package com.example.shovip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentVoiceCallBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class VoiceCallFragment : Fragment() {
    private var binding by autoCleared<FragmentVoiceCallBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVoiceCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_VoiceCallFragment_to_VideoCallFragment)
        }
        binding.bHangup.setOnClickListener {
            // TODO: Actually hang up the call! You need access to the call object here!
            findNavController().navigate(R.id.action_VoiceCallFragment_to_DialPadFragment)
        }

        // TODO: 1. Create the SipAudioCall.Listener

        // TODO: 2. In onCallEnded() of the SipAudioCall.Listener, you should close this view and return to the dialpad
    }
}
