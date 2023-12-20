package com.suatzengin.iloveanimals.ui.veterinaryclinic.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.components.button.IlaButton

@Composable
fun ClinicMoreInfo(
    times: String,
    doctor: String,
    onDirectionClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = times, color = colorResource(id = R.color.color_edit_text_stroke))
        }

        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_vet),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = doctor, color = colorResource(id = R.color.color_edit_text_stroke))
        }

        IlaButton(
            onClick = onDirectionClick, modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_direction), contentDescription = "",
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "Yol tarifi")
        }
    }
}
