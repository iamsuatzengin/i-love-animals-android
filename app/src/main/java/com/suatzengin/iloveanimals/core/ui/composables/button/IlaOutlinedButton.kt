package com.suatzengin.iloveanimals.core.ui.composables.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R

@Composable
fun IlaOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    cornerSize: Dp = 16.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = colorResource(id = R.color.color_primary),
    contentColor: Color = colorResource(id = R.color.color_primary),
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides ButtonRippleTheme) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            border = BorderStroke(
                width = borderWidth,
                color = borderColor
            ),
            shape = RoundedCornerShape(cornerSize),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = contentColor),
            content = content
        )
    }
}
