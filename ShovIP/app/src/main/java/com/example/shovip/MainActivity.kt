package com.example.shovip

import android.content.Context
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.onNavDestinationSelected
import com.example.shovip.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val sipManager: SipManager? by lazy(LazyThreadSafetyMode.NONE) {
        SipManager.newInstance(this)
    }
    var sipProfile : SipProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun reloadSipProfile() {
        // TODO: This is where we start working with SIP, so request the permissions here
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_SIP)
            == PackageManager.PERMISSION_GRANTED){
        }else{
            ActivityCompat.requestPermissions(this, Manifest.permission.USE_SIP, 0);
        }
        sipProfile?.let {
            // TODO: Cleanup any old SipProfile, add "closeProfile" code here
            if(sipManager == null){
            try {
                sipManager?.close(sipProfile?.uriString)
            } catch (ee: Exception) {
                Log.d("WalkieTalkieAct/onDes", "Failed to close local profile.", ee)
            }
        }}

        // Create SipProfile
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val builder = SipProfile.Builder(sharedPreferences.getString("USERNAME", ""), sharedPreferences.getString("DOMAIN", ""))
            .setPassword(sharedPreferences.getString("PASSWORD", ""))
        sipProfile = builder.build()

        // Register to the SIP server
        sipManager?.open(sipProfile, null, null)
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