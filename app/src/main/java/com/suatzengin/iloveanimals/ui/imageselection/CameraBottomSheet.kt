package com.suatzengin.iloveanimals.ui.imageselection

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.CameraManager
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentCameraBottomSheetBinding
import com.suatzengin.iloveanimals.util.extension.showSnackbar

class CameraBottomSheet : BottomSheetDialogFragment(R.layout.fragment_camera_bottom_sheet) {
    private val binding by viewBinding(FragmentCameraBottomSheetBinding::bind)

    private val cameraManager: CameraManager by lazy {
        CameraManager(
            requireContext(),
            viewLifecycleOwner
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraManager.startCamera(
            surfaceProvider = binding.cameraPreview.surfaceProvider,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        )

        binding.apply {
            imageCaptureButton.setOnClickListener {

                cameraManager.takePhoto(
                    onSuccess = { uri ->
                        showSnackbar(type = SnackbomType.INFO, "Kaydedildi! $uri")
                    }
                )
            }

            btnDismiss.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout?>(com.google.android.material.R.id.design_bottom_sheet)

            val behavior = BottomSheetBehavior.from(bottomSheet)

            val layoutParams = bottomSheet.layoutParams

            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

            bottomSheet.layoutParams = layoutParams

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }
}
