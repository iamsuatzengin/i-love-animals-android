package com.suatzengin.iloveanimals.ui.firstaidguide.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R

@Composable
fun FirstAidGuideDetailHeader(
    title: String,
    detailImageResId: Int
) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            painter = painterResource(id = R.drawable.wave),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = colorResource(id = R.color.color_location_icon)),
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 48.dp),
            painter = painterResource(id = detailImageResId),
            contentDescription = "",
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.h6
        )

        Text(
            text = "Ne yapabilirim?",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp)
        )
    }
}
