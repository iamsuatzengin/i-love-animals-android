package com.suatzengin.iloveanimals.core.ui.components.button

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.suatzengin.iloveanimals.R

object ButtonRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = colorResource(id = R.color.color_primary)

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = Color.Black, lightTheme = true
    )
}
