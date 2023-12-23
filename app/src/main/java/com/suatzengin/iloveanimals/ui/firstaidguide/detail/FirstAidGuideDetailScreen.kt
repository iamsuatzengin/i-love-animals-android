package com.suatzengin.iloveanimals.ui.firstaidguide.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.suatzengin.iloveanimals.ui.firstaidguide.composables.FirstAidGuideDetailHeader
import com.suatzengin.iloveanimals.ui.firstaidguide.composables.FirstAidGuideStepList
import com.suatzengin.iloveanimals.ui.firstaidguide.model.FirstAidUiModel

@Composable
fun FirstAidGuideDetailScreen(firstAidUiModel: FirstAidUiModel) {
    val groupedSteps = firstAidUiModel.stepList.mapIndexed { index, string ->
        index + 1 to string
    }.toMap()

    Column {
        FirstAidGuideDetailHeader(
            title = firstAidUiModel.title,
            detailImageResId = firstAidUiModel.detailImageResId
        )

        FirstAidGuideStepList(grouped = groupedSteps)
    }
}
