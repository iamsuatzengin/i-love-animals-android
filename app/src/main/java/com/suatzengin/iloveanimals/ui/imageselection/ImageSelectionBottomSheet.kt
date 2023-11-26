package com.suatzengin.iloveanimals.ui.imageselection

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentImageSelectionBottomSheetBinding

class ImageSelectionBottomSheet(
    private val onTakePictureClick: () -> Unit,
    private val onPickImageFromGalleryClick: () -> Unit,
) : BottomSheetDialogFragment(R.layout.fragment_image_selection_bottom_sheet) {
    private val binding by viewBinding(FragmentImageSelectionBottomSheetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            layoutTakePicture.setOnClickListener {
                onTakePictureClick()
                dismiss()
            }

            layoutPickImageFromGallery.setOnClickListener {
                onPickImageFromGalleryClick()
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout?>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        }

        return dialog
    }
}
