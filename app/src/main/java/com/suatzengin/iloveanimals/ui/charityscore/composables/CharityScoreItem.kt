package com.suatzengin.iloveanimals.ui.charityscore.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore

@Composable
fun CharityScoreItem(
    rank: Int,
    item: CharityScore,
    background: Color,
    rankIcon: Int?,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (rankIcon != null) {
            Image(painter = painterResource(id = rankIcon), contentDescription = "")
        } else {
            Text(text = "$rank", color = Color.Gray, fontSize = 16.sp)
        }

        AsyncImage(
            model = item.userImageUrl,
            contentDescription = "",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(text = item.userFullName, modifier = Modifier.weight(1f), fontSize = 16.sp)

        Text(
            text = "${item.point}", fontSize = 16.sp,
            color = colorResource(id = R.color.color_delete_button), fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption
        )
    }
}
