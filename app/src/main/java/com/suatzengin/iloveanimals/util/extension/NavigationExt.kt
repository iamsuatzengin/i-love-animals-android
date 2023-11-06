package com.suatzengin.iloveanimals.util.extension

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.android.material.bottomnavigation.BottomNavigationView

fun NavDestination?.isTopLevelDestinationInHierarchy(destinationId: Int) =
    this?.hierarchy?.any { it.id == destinationId } == true

fun BottomNavigationView.topLevelNavigateListener(navController: NavController) {
    setOnItemSelectedListener { menuItem ->
        navController.navigate(menuItem.itemId, null, navOptions(navController))
        navController.currentDestination.isTopLevelDestinationInHierarchy(menuItem.itemId)
    }
}
private fun navOptions(navController: NavController) = androidx.navigation.navOptions {
    launchSingleTop = true
    restoreState = true

    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
}