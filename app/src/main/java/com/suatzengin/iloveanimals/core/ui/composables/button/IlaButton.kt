package com.suatzengin.iloveanimals.core.ui.composables.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
fun IlaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerSize: Dp = 16.dp,
    contentColor: Color = Color.White,
    backgroundColor: Color = colorResource(id = R.color.color_primary),
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides ButtonRippleTheme) {
        Button(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(cornerSize),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor
            ),
            content = content
        )
    }
}
