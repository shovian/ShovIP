package com.example.shovip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shovip.databinding.FragmentDialPadBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DialPadFragment : Fragment() {

    private var _binding: FragmentDialPadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDialPadBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_DialPadFragment_to_VoiceCallFragment)
        }
        with(binding){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}