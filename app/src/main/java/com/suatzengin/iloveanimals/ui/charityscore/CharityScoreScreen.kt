package com.suatzengin.iloveanimals.ui.charityscore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.ui.charityscore.composables.CharityScoreHeader
import com.suatzengin.iloveanimals.ui.charityscore.composables.CharityScoreItem

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
                    CharityScoreHeader(
                        currentUserRank = uiState.currentUserRank,
                        currentUserFullName = uiState.currentUserScore?.userFullName.orEmpty()
                    )
                }

                itemsIndexed(items = uiState.scores) { index, item ->
                    val itemBackground =
                        if (index % 2 != 0) colorResource(id = R.color.color_light_gray) else Color.White

                    CharityScoreItem(
                        rank = index + 1,
                        item = item,
                        background = itemBackground,
                        rankIcon = uiState.getFirstThreeRankIcon(index = index)
                    )
                }
            }
        }
    }
}
