package com.suatzengin.iloveanimals.core.ui.composables.imagepager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalImagePager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    images: List<String>
) {
    Box {
        HorizontalPager(
            state = pagerState,
            modifier = modifier.clip(RoundedCornerShape(10.dp))

        ) {
            AsyncImage(
                model = images[it], contentDescription = "Resim",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        PagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage
        )
    }
}
