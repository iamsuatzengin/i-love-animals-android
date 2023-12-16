package com.suatzengin.iloveanimals.ui.veterinaryclinic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.ui.veterinaryclinic.component.VeterinaryClinicItem

@Composable
fun VeterinaryClinicScreen(
    viewModel: VeterinaryClinicViewModel,
    onNavigateBackClick: () -> Unit,
    onDirectionButtonClick: (lat: String, long: String) -> Unit,
    onCallButtonClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(id = R.color.color_primary)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                TopAppBar(
                    title = {
                        Text(text = "Klinikler")
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

            items(state.clinics) { clinic ->
                VeterinaryClinicItem(
                    clinic = clinic.clinicName,
                    address = clinic.address.address,
                    isAmbulanceAvailable = clinic.isAmbulanceAvailable,
                    doctor = clinic.doctorName,
                    times = "${clinic.openTimes}pm - ${clinic.closeTimes}",
                    images = clinic.images,
                    onDirectionButtonClick = {
                        onDirectionButtonClick(clinic.address.latitude, clinic.address.longitude)
                    },
                    onCallButtonClick = {
                        onCallButtonClick(clinic.phoneNumber)
                    }
                )
            }
        }
    }
}
