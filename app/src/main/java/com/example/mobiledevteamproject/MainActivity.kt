package com.example.mobiledevteamproject

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    private lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var navView: NavigationView
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //---------------------------- FOR FIRESTORE DATABASE ----------------------------//
        db = Firebase.firestore

        db.collection("meme")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("GET MEME", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("GET MEME", "Error getting documents: ", exception)
            }

        //---------------------------- FOR BOTTOM NAV MENU ----------------------------//


        //get reference to the NavHost fragment to get reference to the NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        //setup BottomNav
        findViewById<BottomNavigationView>(R.id.bottomNavigationView_main).setupWithNavController(navController)

        //---------------------------- FOR HAMBURGER MENU ----------------------------//

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_main)
        navView = findViewById<NavigationView>(R.id.navigationView_main)

        // Set up the ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up the navigation listener
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle the navigation item clicks here
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        // Set up the navigation graph
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START) // close drawer layout
        return true
    }

}