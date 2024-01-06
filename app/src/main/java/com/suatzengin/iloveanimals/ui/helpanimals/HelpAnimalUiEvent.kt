package com.suatzengin.iloveanimals.ui.helpanimals

import com.suatzengin.iloveanimals.R

sealed interface HelpAnimalUiEvent {
    class Approved(val message: Int = R.string.text_helping_approved) : HelpAnimalUiEvent
    class Denied(val message: Int = R.string.text_helping_denied) : HelpAnimalUiEvent

    class CompletedAndNavigateAdList(val successMessage: String) : HelpAnimalUiEvent
}
