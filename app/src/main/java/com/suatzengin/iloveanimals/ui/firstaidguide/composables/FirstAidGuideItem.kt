package com.suatzengin.iloveanimals.ui.firstaidguide.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.ui.firstaidguide.model.FirstAidUiModel

@Composable
fun FirstAidGuideItem(
    item: FirstAidUiModel,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.color_light_gray),
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onItemClick)
            .padding(16.dp)
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = item.thumbnailResId), contentDescription = "",
            modifier = Modifier
        )
        Text(
            text = item.title,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}
