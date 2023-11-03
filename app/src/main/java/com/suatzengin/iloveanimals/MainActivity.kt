package com.suatzengin.iloveanimals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authHandler.isLogin) {
            navController.navigate(R.id.to_advertisementGraph)
        }

        initView(navController = navController)
    }

    private fun initView(navController: NavController) = with(binding) {
        bottomNavigationBar.setupWithNavController(navController = navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    window.statusBarColor = resources.getColor(R.color.color_primary, null)
                    bottomNavigationBar.isVisible = false
                }

                else -> {
                    window.statusBarColor = resources.getColor(R.color.white, null)
                    bottomNavigationBar.isVisible = true
                }
            }
        }
    }
}