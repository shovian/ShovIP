package com.example.shovip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentVideoCallBinding

class VideoCallFragment : Fragment() {
    private var _binding: FragmentVideoCallBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVideoCallBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button11.setOnClickListener(){
            findNavController().navigate(R.id.action_VideoCallFragment_to_VoiceCallFragment)
        }
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_VideoCallFragment_to_DialPadFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}