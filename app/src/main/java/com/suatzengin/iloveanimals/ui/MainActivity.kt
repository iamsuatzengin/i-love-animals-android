package com.suatzengin.iloveanimals.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authHandler: IlaAuthHandler

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authHandler.isLogin) {
            navController.navigate(R.id.to_advertisementGraph)
        }

        bottomNavigationBar.setupWithNavController(navController = navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            val bottomNavigationIsVisible =
                dest.id != R.id.loginFragment || dest.id != R.id.registerFragment

            bottomNavigationBar.isVisible = bottomNavigationIsVisible
        }
    }
}