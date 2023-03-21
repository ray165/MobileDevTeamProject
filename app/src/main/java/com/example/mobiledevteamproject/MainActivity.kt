package com.example.mobiledevteamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //---------------------------- FOR BOTTOM NAV MENU ----------------------------//


        //get reference to the NavHost fragment to get reference to the NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        //setup BottomNav
        findViewById<BottomNavigationView>(R.id.bottomNavigationView_main).setupWithNavController(navController)

        //---------------------------- FOR HAMBURGER MENU ----------------------------//

        //this is the hamburger button in the drawlayout
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //the parent
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_main)

        //the last child (the sliding menu from the left)
        navigationView = findViewById(R.id.navigationView_main)
        navigationView.setNavigationItemSelectedListener(this)


        //for the hamburger button. Include string for open button text, and close button text. Add in strings.xml
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    //BOTTOM NAV - Need to implement for PopupMenu interface
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        println(item?.title)
        return true
    }

    //HAMBURGER NAV - override this so the toggle will open and close
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return false
    }

    //HAMBURGER NAV - this is to enable listening of the button clicks in the drawer layout
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            //if select Home on hamburger menu, replace fragment to HomeFragment
            R.id.menuItem_hamburger_home -> supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView_main, HomeFragment()
            ).commit()

            //if select Likes on hamburger menu, replace fragment to LikeFragment
            R.id.menuItem_hamburger_likes -> supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView_main, LikeFragment()
            ).commit()

            //if select About on hamburger menu, replace fragment to LikeFragment
            R.id.menuItem_hamburger_about -> supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView_main, AboutFragment()
            ).commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START) // close drawer layout
        return true
    }


}