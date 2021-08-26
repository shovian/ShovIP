package com.example.shovip

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.sip.SipAudioCall
import android.net.sip.SipManager
import android.net.sip.SipProfile
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.shovip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    public var num : String = ""
    lateinit var callReceiver: IncomingCallReceiver
    public interface MyCallListener {
        fun onCalling() {}
        fun onCallEstablished() {}
        fun onCallEnded(call: SipAudioCall) {}
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val sipManager: SipManager? by lazy(LazyThreadSafetyMode.NONE) {
        SipManager.newInstance(this)
    }
    var sipProfile : SipProfile? = null
    var call : SipAudioCall? = null
    var myCallListener : MyCallListener? = null

    var audioCallListener: SipAudioCall.Listener = object : SipAudioCall.Listener() {
        override fun onCalling(call: SipAudioCall?) {
            Log.v("ShovIP", "SipAudioCall.Listener.onCalling()")
            myCallListener?.let {
                it.onCalling()
            } ?: run {
                Log.v("ShovIP", "myCallListener is null!")
            }

            super.onCalling(call)
        }

        override fun onCallEstablished(call: SipAudioCall) {
            Log.v("ShovIP", "SipAudioCall.Listener.onCallEstablished()")
            call.apply {
                Log.v("ShovIP", "starting audio...")
                startAudio()
            }
            myCallListener?.onCallEstablished()
            super.onCallEstablished(call)
        }

        override fun onCallEnded(call: SipAudioCall) {
            Log.v("ShovIP", "SipAudioCall.Listener.onCallEnded()")
            myCallListener?.onCallEnded(call)
        }
    }

    fun navigateToVoiceCallFragment() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_DialPadFragment_to_VoiceCallFragment)
    }

    fun dial(number: String): Boolean {
        num=number
        if( !number.isNullOrEmpty() ){
            Log.v("ShovIP", "Dialing $number...")

            try {
                val sharedPreferences = getSharedPreferences(
                        "sharedPrefs",
                        Context.MODE_PRIVATE
                    )
                val domain = sharedPreferences.getString("DOMAIN", "")
                val proxy = sharedPreferences.getString("PROXY", "")
                val builder = SipProfile.Builder(number, domain)
                if (!proxy.isNullOrEmpty()) {
                    builder.setOutboundProxy(proxy)
                }
                var callee = builder.build()

                callee?.let {
                    Log.v("ShovIP", "Created callee profile: ${callee.uriString}")
                } ?: run {
                    Log.v("ShovIP", "Could not create profile")
                    return false
                }

                call = sipManager?.makeAudioCall(
                    sipProfile,
                    callee,
                    audioCallListener,
                    0
                )

                if ( call == null ) {
                    Log.v("ShovIP", "Could not dial call.")
                    return false
                }

                return true
            } catch (ee: Exception) {
                Log.d("ShovIP", "Failed to dial.", ee)
            }

            return false
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val filter = IntentFilter().apply {
            addAction("com.example.shovip.INCOMING_CALL")
        } 
        callReceiver = IncomingCallReceiver()
        this.registerReceiver(callReceiver, filter)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun requestPermission() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.USE_SIP) != PackageManager.PERMISSION_GRANTED ){
            Log.v("ShovIP", "We don't have USE_SIP permission, requesting it...")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.USE_SIP), 0)
        } else {
            Log.v("ShovIP", "We already have USE_SIP permission.")
        }

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ){
            Log.v("ShovIP", "We don't have RECORD_AUDIO permission, requesting it...")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        } else {
            Log.v("ShovIP", "We already have RECORD_AUDIO permission.")
        }

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED ){
            Log.v("ShovIP", "We don't have MODIFY_AUDIO_SETTINGS permission, requesting it...")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.MODIFY_AUDIO_SETTINGS), 0)
        } else {
            Log.v("ShovIP", "We already have MODIFY_AUDIO_SETTINGS permission.")
        }

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED ){
            Log.v("ShovIP", "We don't have ACCESS_WIFI_STATE permission, requesting it...")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_WIFI_STATE), 0)
        } else {
            Log.v("ShovIP", "We already have ACCESS_WIFI_STATE permission.")
        }

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED ){
            Log.v("ShovIP", "We don't have WAKE_LOCK permission, requesting it...")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WAKE_LOCK), 0)
        } else {
            Log.v("ShovIP", "We already have WAKE_LOCK permission.")
        }

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.USE_SIP) != PackageManager.PERMISSION_GRANTED ){
            // display an error dialog
            Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
            //  exit the app
            Log.v("ShovIP", "Permission not given, Closing program...")
            finish()
            System.exit(-1)
        }
    }

    fun reloadSipProfile() {
        Log.v("ShovIP", "Reloading SipProfile...")
        requestPermission()

        sipProfile?.let {
            // Cleanup any old SipProfile, add "closeProfile" code here
            try {
                Log.v("ShovIP", "Closing old SipProfile...")
                sipManager?.close(it.uriString)
            } catch (ee: Exception) {
                Log.d("ShovIP", "Failed to close local profile.", ee)
            }
        }

        // Create SipProfile
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("USERNAME", "")
        val domain = sharedPreferences.getString("DOMAIN", "")
        val password = sharedPreferences.getString("PASSWORD", "")
        val proxy = sharedPreferences.getString("PROXY", "")
        try{
            if ( !user.isNullOrEmpty() && !domain.isNullOrEmpty() ) {
                Log.v("ShovIP", "Building SipProfile for $user@$domain")
                val builder = SipProfile.Builder(user, domain)
                    .setPassword(password)

                if ( !proxy.isNullOrEmpty() ) {
                    builder.setOutboundProxy(proxy)
                }

                sipProfile = builder.build()
                sipProfile?.let {
                    // Register to the SIP server
                    Log.v("ShovIP", "Starting SIP registration...")
                    val intent = Intent("com.example.shovip.INCOMING_CALL")
                    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA)
                    sipManager?.open(it, pendingIntent, null)
                } ?: run {
                    Log.v("ShovIP", "Could not create SipProfile")
                }
            } else {
                Log.v("ShovIP", "Skipped building SipProfile because user or domain is empty: user = $user, domain = $domain")
            }
        }
        catch (ee : Exception){
            Log.d("ShovIP","error happening", ee)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}