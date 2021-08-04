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

    private var _binding: FragmentVoiceCallBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVoiceCallBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button11.setOnClickListener {
            findNavController().navigate(R.id.action_VoiceCallFragmentto_VideoCallFragment)
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