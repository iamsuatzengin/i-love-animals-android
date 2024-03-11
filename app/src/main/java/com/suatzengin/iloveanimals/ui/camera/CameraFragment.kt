package com.suatzengin.iloveanimals.ui.camera

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.CameraManager
import com.suatzengin.iloveanimals.core.cameraManager
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentCameraBinding
import com.suatzengin.iloveanimals.ui.camera.adapter.CameraAdapter
import com.suatzengin.iloveanimals.util.Constants.MAX_IMAGES
import kotlinx.coroutines.launch

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val binding by viewBinding(FragmentCameraBinding::bind)

    private val viewModel by viewModels<CameraViewModel>()

    private val adapter by lazy { CameraAdapter(::onImageDeleteClick) }

    private val cameraManager: CameraManager by lazy {
        cameraManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraManager.startCamera(
            binding.cameraPreview.surfaceProvider,
            CameraSelector.DEFAULT_BACK_CAMERA
        )

        setupRecyclerView()
        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { list ->
                    initView(list)
                }
            }
        }
    }

    private fun initView(imageUris: List<Uri>) = with(binding) {
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        adapter.submitList(imageUris)

        if (imageUris.size == MAX_IMAGES) {
            handleImageCapturedIsDone(imageUris)
        } else {
            takePhoto()
        }

        btnDone.setOnClickListener {
            navigateToCreateAdvertisement(imageUris)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvImages

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = adapter
    }

    private fun takePhoto() {
        binding.imageCaptureButton.setImageResource(R.drawable.ic_camera)

        binding.imageCaptureButton.setOnClickListener {
            cameraManager.takePhoto { uri ->
                // handle when photo captured
                if (uri != null) {
                    viewModel.updateImageList(uri)
                }
            }
        }
    }

    private fun handleImageCapturedIsDone(imageUris: List<Uri>) {
        binding.imageCaptureButton.setImageResource(R.drawable.ic_done)

        binding.imageCaptureButton.setOnClickListener {
            navigateToCreateAdvertisement(imageUris)
        }
    }

    private fun navigateToCreateAdvertisement(imageUris: List<Uri>) {
        val list = imageUris.map { it.toString() }.toTypedArray()

        val bundle = Bundle().apply {
            putStringArray(IMAGE_LIST_BUNDLE_KEY, list)
        }

        setFragmentResult(IMAGE_LIST_REQUEST_KEY, bundle)

        findNavController().navigateUp()
    }

    private fun onImageDeleteClick(uri: Uri) {
        viewModel.deleteImage(uri)
    }

    companion object {
        const val IMAGE_LIST_REQUEST_KEY = "imageListRequestKey"
        const val IMAGE_LIST_BUNDLE_KEY = "imageListBundleKey"
    }
}
