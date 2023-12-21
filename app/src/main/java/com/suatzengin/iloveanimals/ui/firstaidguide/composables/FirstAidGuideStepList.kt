package com.suatzengin.iloveanimals.ui.firstaidguide.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FirstAidGuideStepList(grouped: Map<Int, String>) {
    LazyColumn(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        grouped.forEach { (step, text) ->
            stickyHeader {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Adım $step",
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
