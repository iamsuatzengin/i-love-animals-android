package com.suatzengin.iloveanimals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authHandler: IlaAuthHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authHandler.isLogin) {
            navController.navigate(R.id.to_advertisementGraph)
        }

        navController.addOnDestinationChangedListener { _, dest, _ ->
            when(dest.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    window.statusBarColor = resources.getColor(R.color.color_primary, null)
                }
                else -> {
                    window.statusBarColor = resources.getColor(R.color.white, null)
                }
            }
        }
    }
}