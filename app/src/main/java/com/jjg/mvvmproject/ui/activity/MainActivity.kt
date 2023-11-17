package com.jjg.mvvmproject.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jjg.mvvmproject.R
import com.jjg.mvvmproject.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.navHostFragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            controller.currentDestination?.id.let { _destinationId ->
                when (_destinationId) {
                    R.id.newsFragment,
                    R.id.searchFragment,
                    R.id.recentFragment -> {
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNavigationView.visibility = View.GONE
                    }
                }
            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}