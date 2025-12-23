package com.example.christmasappadventcalendar

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var repo: UserPreferencesRepository
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        repo = UserPreferencesRepository(this)

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        // Najdeme toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)

        // Nastavíme ho jako ActionBar
        setSupportActionBar(toolbar)

        // Najdeme DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Vytvoříme ActionBarDrawerToggle pro hamburger menu
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        // Přidáme listener pro drawer
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Propojíme navigace s menu
        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setupWithNavController(navController)

        findViewById<NavigationView>(R.id.drawerNav)
            .setupWithNavController(navController)

        // SLEDOVÁNÍ HODNOT (Flow → UI)
        /*lifecycleScope.launch {
            repo.usernameFlow.collectLatest { value ->
                binding.textPreview.text = value.ifEmpty { "Ukázkový text" }
                binding.editUsername.setText(value)
            }
        }

        // REAKCE NA UI A ULOŽENÍ DO DATASTORE
        binding.buttonSaveUsername.setOnClickListener {
            val name = binding.editUsername.text.toString()
            lifecycleScope.launch {
                repo.setUsername(name)
            }
        }*/
    }
}