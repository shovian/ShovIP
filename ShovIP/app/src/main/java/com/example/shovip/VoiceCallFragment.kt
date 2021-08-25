package com.example.shovip

import android.content.Context
import android.net.sip.SipAudioCall
import android.net.sip.SipSession
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
                    updateCallState()
                }
            }

            override fun onCallEstablished() {
                Log.v("ShovIP", "MyCallListener.onCallEstablished()")

                activity?.runOnUiThread {
                    updateCallState()
                }
            }

            override fun onCallEnded(call: SipAudioCall) {
                Log.v("ShovIP", "MyCallListener.onCallEnded()")

                call.close()
                call.setListener(null)  // remove ourselves as listener so there are no dangling references
                mainActivity.call = null
                mainActivity.myCallListener = null

                activity?.runOnUiThread {
                    updateCallState()
                    findNavController().navigate(R.id.action_VoiceCallFragment_to_DialPadFragment)
                }
            }
        }

        mainActivity.myCallListener = myCallListener
        updateCallState()
    }

    private fun updateCallState() {
        try {
            val mainActivity = activity as MainActivity
            var stateText = "Idle"

            mainActivity.call?.let {
                stateText = when (it.state) {
                    SipSession.State.INCOMING_CALL, SipSession.State.INCOMING_CALL_ANSWERING -> "Ringing"
                    SipSession.State.OUTGOING_CALL, SipSession.State.OUTGOING_CALL_RING_BACK -> "Dialing"
                    SipSession.State.IN_CALL -> "Connected"
                    SipSession.State.OUTGOING_CALL_CANCELING -> "Cancelling"
                    SipSession.State.READY_TO_CALL, SipSession.State.DEREGISTERING, SipSession.State.REGISTERING -> "Idle"
                    else -> "Unknown state"
                }
            } ?: run {
                stateText = "Idle"
            }

            Log.v("ShovIP", "stateText = $stateText")
            binding.tvStatus.text = stateText
        } catch (ee: Exception) {
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).call?.let {
            Log.v("ShovIP", "Hanging up the call...")
            it.endCall()
        }
    }
}
