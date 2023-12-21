package com.suatzengin.iloveanimals.ui.firstaidguide

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.ui.firstaidguide.composables.FirstAidGuideItem
import com.suatzengin.iloveanimals.ui.firstaidguide.model.FirstAidUiModel
import com.suatzengin.iloveanimals.ui.firstaidguide.model.firstAidGuides

@Composable
fun FirstAidGuidesScreen(
    onNavigateBackClick: () -> Unit,
    onItemClick: (FirstAidUiModel) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "İlk Yardım Rehberi")
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.ic_back),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(onClick = onNavigateBackClick)
                    )
                },
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(innerPadding)
                .padding(
                    start = 16.dp, end = 16.dp, top = 16.dp
                ),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 8.dp
        ) {
            items(items = firstAidGuides) { uiModel ->
                FirstAidGuideItem(
                    item = uiModel,
                    onItemClick = {
                        onItemClick(uiModel)
                    }
                )
            }
        }
    }
}
