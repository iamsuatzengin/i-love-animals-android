package com.suatzengin.iloveanimals.ui.charityscore.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suatzengin.iloveanimals.R

@Composable
fun CharityScoreHeader(
    currentUserRank: Int,
    currentUserFullName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colorResource(id = R.color.color_charity_bg)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(124.dp),
            painter = painterResource(id = R.drawable.ic_cat_with_cup),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier,
            text = "Sıralaman:\n#${currentUserRank} $currentUserFullName",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp
        )
    }
}
