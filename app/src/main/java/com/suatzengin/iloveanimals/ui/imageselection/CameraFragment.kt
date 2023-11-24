package com.suatzengin.iloveanimals.ui.imageselection

import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.CameraManager
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentCameraBinding
import com.suatzengin.iloveanimals.util.extension.showSnackbar


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val binding by viewBinding(FragmentCameraBinding::bind)

    private val cameraManager: CameraManager by lazy {
        CameraManager(
            requireContext(),
            viewLifecycleOwner
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraManager.startCamera(
            binding.cameraPreview.surfaceProvider,
            CameraSelector.DEFAULT_BACK_CAMERA
        )

        initView()
    }

    private fun initView() = with(binding) {

        imageCaptureButton.setOnClickListener {

            cameraManager.takePhoto(
                onPhotoCaptured = { uri ->
                    showSnackbar(type = SnackbomType.INFO, "Kaydedildi! $uri")
                    println("camera $uri")
                }
            )
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
