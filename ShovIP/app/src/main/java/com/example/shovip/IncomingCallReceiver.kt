package com.example.shovip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.sip.SipAudioCall
import android.util.Log

class IncomingCallReceiver : BroadcastReceiver() {
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * WalkieTalkieActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    override fun onReceive(context: Context, intent: Intent) {
        val mainActivity = context as MainActivity
        Log.v("ShovIP","IncomingCallReceiver.onReceive()")

        var incomingCall: SipAudioCall? = null
        try {
            incomingCall = mainActivity.sipManager?.takeAudioCall(intent, mainActivity.audioCallListener)
            incomingCall?.apply {
                // TODO: Don't answer the call here. Instead, play a ringtone sound!
                Log.v("ShovIP","Answering call...")
                answerCall(30)

                mainActivity.call = this
                mainActivity.navigateToVoiceCallFragment()
            }
        } catch (e: Exception) {
            incomingCall?.close()
        }
    }
}
