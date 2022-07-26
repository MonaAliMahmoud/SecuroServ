package com.securoserv.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.securoserv.R
import com.securoserv.ui.base.di.DIBaseActivity
import com.securoserv.ui.home.HomeFragment
import com.securoserv.ui.maps.zonesmap.ZonesMapFragment

class MainActivity : DIBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    var currentFrame: Int = 0
    lateinit var tvBarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFrame = R.id.home

        if (savedInstanceState == null) {
            val homeFragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.content_frame, homeFragment)
            fragmentTransaction.commit()
        }

        val toolbar: Toolbar = this.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        tvBarTitle = this.findViewById(R.id.tvBarTitle)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedScreen(itemId: Int) {

        var fragment: Fragment? = null

        when (itemId) {
            R.id.home -> {
                fragment = HomeFragment()
                tvBarTitle.text = "Home"
            }
            R.id.live_stream -> {
                fragment = ZonesMapFragment()
                tvBarTitle.text = "Live Stream"
            }
            R.id.recorded_videos -> {
                fragment = ZonesMapFragment()
                tvBarTitle.text = "Recorded Videos"
            }
            R.id.saved_events -> {
                fragment = ZonesMapFragment()
                tvBarTitle.text = "Saved Events"
            }
            R.id.settings -> {
                fragment = ZonesMapFragment()
                tvBarTitle.text = "Settings"
            }
        }

        if (fragment != null) {
            val frgMng = supportFragmentManager
            val frgTran = frgMng.beginTransaction()
            frgTran.replace(R.id.content_frame, fragment).addToBackStack(null).commit()
        }
    }
}