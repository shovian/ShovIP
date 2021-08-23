package com.example.shovip

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import androidx.navigation.ui.onNavDestinationSelected
import com.example.shovip.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val sipManager: SipManager? by lazy(LazyThreadSafetyMode.NONE) {
        SipManager.newInstance(this)
    }
    var sipProfile : SipProfile? = null
    // TODO: Add call object here
    var call : SipAudioCall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.USE_SIP), 0);
        } else {
            Log.v("ShovIP", "We already have USE_SIP permission.")
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

                    // TODO: This intent is from an Android demo project and will not work
                    val intent = Intent("android.SipDemo.INCOMING_CALL")
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