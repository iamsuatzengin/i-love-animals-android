package com.suatzengin.iloveanimals.ui.veterinaryclinic.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R

@Composable
fun ClinicsEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.vec_no_result_vet),
            contentDescription = ""
        )

        Text(
            text = stringResource(R.string.text_nearby_vet_clinic_not_found),
            color = Color.Black, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
