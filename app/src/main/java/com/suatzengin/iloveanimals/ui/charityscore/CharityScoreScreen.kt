package com.suatzengin.iloveanimals.ui.charityscore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore

@Composable
fun CharityScoreScreen(viewModel: CharityScoreViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.padding(top = 48.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Yardımseverlik Tablosu")
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                item {
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
                            text = "Sıralaman:\n#4 Suat Zengin",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 18.sp
                        )
                    }
                }

                itemsIndexed(items = uiState.scores) { index, item ->
                    CharityScoreItem(index = index, item = item)
                }
            }
        }
    }
}

@Composable
fun CharityScoreItem(
    index: Int,
    item: CharityScore
) {
    val bg = if (index % 2 != 0) colorResource(id = R.color.color_light_gray) else Color.White

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = bg)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${index + 1}", color = Color.Gray, fontSize = 16.sp)

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
