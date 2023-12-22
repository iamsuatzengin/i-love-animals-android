package com.suatzengin.iloveanimals.ui

import android.net.Uri
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.databinding.ActivityMainBinding
import com.suatzengin.iloveanimals.util.extension.delayOnLifecycle
import com.suatzengin.iloveanimals.util.extension.isTopDestination
import com.suatzengin.iloveanimals.util.extension.topLevelNavigateListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authHandler: IlaAuthHandler

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

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

        navController = navHostFragment.navController

        if (authHandler.isLogin.not()) {
            navController.navigate(R.id.to_loginFragment)
        }

        bottomNavigationBar.setupWithNavController(navController = navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            bottomNavigationBar.isVisible = isTopDestination(dest.id)
            fab.isVisible = isTopDestination(dest.id)
            viewModel.updateVisibilityWithAnim(isTopDestination(dest.id))
        }

        bottomNavigationBar.topLevelNavigateListener(navController = navController)

        windowInsetsListener(binding.root)

        initFab()

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    if (state.visibilityWithAnim) {
                        showView(fabAddAdvertisement, state.fabIsVisible)

                        fabAddAdvertisement.delayOnLifecycle(DELAY_ANIM_50) {
                            showView(fabVet, state.fabIsVisible)
                        }

                        fabVet.delayOnLifecycle(DELAY_ANIM_100) {
                            showView(fabGuide, state.fabIsVisible)
                        }
                    } else {
                        fabAddAdvertisement.isVisible = state.fabIsVisible
                        fabVet.isVisible = state.fabIsVisible
                        fabGuide.isVisible = state.fabIsVisible
                    }

                }
            }
        }
    }

    private fun showView(view: View, isVisible: Boolean) {
        val fadeAnimation = Fade()

        TransitionManager.beginDelayedTransition(view.parent as ConstraintLayout, fadeAnimation)

        view.isVisible = isVisible
    }

    private fun initFab() = with(binding) {

        fab.setOnClickListener {
            viewModel.updateVisibility()
        }

        fabAddAdvertisement.setOnClickListener {
            navController.navigate(Uri.parse("ila://host/createAd"))
            viewModel.updateVisibility()
        }

        fabVet.setOnClickListener { }

        fabGuide.setOnClickListener {
            navController.navigate(R.id.to_firstAidGuideGraph)
            viewModel.updateVisibility()
        }
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

    companion object {
        const val DELAY_ANIM_50 = 50L
        const val DELAY_ANIM_100 = 100L
    }
}
