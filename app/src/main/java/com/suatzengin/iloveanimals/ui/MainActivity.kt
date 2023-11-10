package com.suatzengin.iloveanimals.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.databinding.ActivityMainBinding
import com.suatzengin.iloveanimals.util.extension.isTopDestination
import com.suatzengin.iloveanimals.util.extension.topLevelNavigateListener
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

    /**
     * topLevelNavigateListener is custom BottomNavigationView extension
     * @see[topLevelNavigateListener]
     */
    private fun initView() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (authHandler.isLogin.not()) {
            navController.navigate(R.id.to_loginFragment)
        }

        bottomNavigationBar.setupWithNavController(navController = navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            bottomNavigationBar.isVisible = isTopDestination(dest.id)
        }

        bottomNavigationBar.topLevelNavigateListener(navController = navController)

        windowInsetsListener(binding.root)
    }

    private fun windowInsetsListener(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = inset.bottom
            }

            WindowInsetsCompat.CONSUMED
        }
    }
}
