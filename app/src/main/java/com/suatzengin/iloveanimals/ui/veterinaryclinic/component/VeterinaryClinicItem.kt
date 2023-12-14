package com.suatzengin.iloveanimals.ui.veterinaryclinic.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.components.button.IlaButton
import com.suatzengin.iloveanimals.core.ui.components.button.IlaOutlinedButton
import com.suatzengin.iloveanimals.core.ui.components.imagepager.HorizontalImagePager

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VeterinaryClinicItem(
    clinic: String = "Clinic Name",
    address: String = "Selvilitepe Mah. Turgutlu/Manisa",
    isAmbulanceAvailable: Boolean = true,
    doctor: String = "Prof Dr Suat F",
    times: String = "09:00 - 20:00",
    images: List<String> = emptyList(),
) {
    var visibility by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState { images.size }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.color_light_gray),
                RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (images.isNotEmpty()) {
            AnimatedVisibility(visible = visibility) {
                HorizontalImagePager(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    pagerState = pagerState,
                    images = images
                )
            }
        }

        Text(text = clinic, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)

        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "Location Icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = address, color = colorResource(id = R.color.color_edit_text_stroke))
        }

        val text = if (isAmbulanceAvailable) "Ambulans mevcut" else "Ambulans mevcut değil"

        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_ambulance_available),
                contentDescription = "",

                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = colorResource(id = R.color.color_edit_text_stroke))
        }

        AnimatedVisibility(visible = visibility) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ClinicMoreInfo(
                    times = times,
                    doctor = doctor,
                    onDirectionClick = {}
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IlaOutlinedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    visibility = !visibility
                }
            ) {
                Text(text = if (visibility) "Detayı kapat" else "Detayı Gör")
            }

            IlaButton(
                modifier = Modifier.weight(1f),
                backgroundColor = colorResource(id = R.color.color_error),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Phone, contentDescription = "",
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Ara")
            }
        }
    }
}
