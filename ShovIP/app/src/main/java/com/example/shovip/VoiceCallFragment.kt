package com.example.shovip

import android.content.Context
import android.net.sip.SipAudioCall
import android.os.Bundle
import android.util.Log
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
        val mainActivity = activity as MainActivity

        binding.bSwitch.setOnClickListener {
            findNavController().navigate(R.id.action_VoiceCallFragment_to_VideoCallFragment)
        }
        binding.bHangup.setOnClickListener {
            mainActivity.call?.let {
                Log.v("ShovIP", "Hanging up the call...")
                it.endCall()
            } ?: run {
                Log.v("ShovIP", "There is no call to hang up.")
                findNavController().navigate(R.id.action_VoiceCallFragment_to_DialPadFragment)
            }
        }

        // Create the SipAudioCall.Listener
        var myCallListener: MainActivity.MyCallListener = object : MainActivity.MyCallListener {
            override fun onCalling() {
                Log.v("ShovIP", "MyCallListener.onCalling()")

                activity?.runOnUiThread {
                    try {
                        binding.tvStatus.text = "Dialing"
                    } catch (ee: Exception) {
                    }
                }
            }

            override fun onCallEstablished() {
                Log.v("ShovIP", "MyCallListener.onCallEstablished()")

                activity?.runOnUiThread {
                    try {
                        binding.tvStatus.text = "Connected"
                    } catch (ee: Exception) {
                    }
                }
            }

            override fun onCallEnded(call: SipAudioCall) {
                Log.v("ShovIP", "MyCallListener.onCallEnded()")

                call.close()
                call.setListener(null)  // remove ourselves as listener so there are no dangling references
                mainActivity.call = null

                activity?.runOnUiThread {
                    try {
                        binding.tvStatus.text = "Ended"
                        findNavController().navigate(R.id.action_VoiceCallFragment_to_DialPadFragment)
                    } catch (ee: Exception) {
                    }
                }
            }
        }

        mainActivity.myCallListener = myCallListener
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).call?.let {
            Log.v("ShovIP", "Hanging up the call...")
            it.endCall()
        }
    }
}
