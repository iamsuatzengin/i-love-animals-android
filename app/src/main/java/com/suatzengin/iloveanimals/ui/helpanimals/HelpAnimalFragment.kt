package com.suatzengin.iloveanimals.ui.helpanimals

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentHelpAnimalBinding
import com.suatzengin.iloveanimals.ui.camera.CameraFragment
import com.suatzengin.iloveanimals.ui.imageselection.ImageSelectionBottomSheet
import com.suatzengin.iloveanimals.util.Constants
import com.suatzengin.iloveanimals.util.extension.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HelpAnimalFragment : Fragment(R.layout.fragment_help_animal) {
    private val binding by viewBinding(FragmentHelpAnimalBinding::bind)

    private val viewModel by viewModels<HelpAnimalViewModel>()

    private val args by navArgs<HelpAnimalFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        collectData()
    }

    private fun initViews() = with(binding) {
        toolbar.setOnLeftIconClick { findNavController().navigateUp() }

        llSelectImage.setOnClickListener {
            val bottomSheet = ImageSelectionBottomSheet(
                onTakePictureClick = ::showCameraFragment,
                onPickImageFromGalleryClick = ::onPickImageClick
            )

            bottomSheet.show(childFragmentManager, BOTTOM_SHEET_TAG)
        }

        btnCheck.setOnClickListener {
            tvConfirmationStatus.isVisible = false

            viewModel.handleHelpApproval(ivTakenImage.drawable.toBitmap())
        }

        btnComplete.setOnClickListener {
            viewModel.completeAdvertisement(
                advertisementId = args.advertisementId,
                advertisementCreatorId = args.adCreatorId
            )
        }

        getImagesFromCamera()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        initImages(uiState.images)
                        binding.tvGeminiResponse.text = uiState.responseText.trimStart()

                        binding.linearProgress.isVisible = uiState.isLoading
                    }
                }

                launch {
                    viewModel.uiEvent.collectLatest { uiEvent ->
                        handleUiEvent(event = uiEvent)
                    }
                }
            }
        }
    }

    private fun initImages(list: List<Uri>) {
        if (list.isEmpty()) return

        val taken2Item = list.take(1)

        binding.ivTakenImage.load(taken2Item[0])
        binding.ivTakenImage.isVisible = true
        binding.btnAddImage.isVisible = false
    }

    private fun handleUiEvent(event: HelpAnimalUiEvent) {
        when (event) {
            is HelpAnimalUiEvent.Approved -> {
                binding.btnComplete.isVisible = true
                initGeminiResponseView(event.message, R.color.color_primary)
            }

            is HelpAnimalUiEvent.Denied -> {
                binding.btnComplete.isVisible = false
                initGeminiResponseView(event.message, R.color.color_error)
            }

            is HelpAnimalUiEvent.CompletedAndNavigateAdList -> {
                showSnackbar(
                    SnackbomType.SUCCESS,
                    text = event.successMessage
                )

                findNavController().popBackStack(
                    R.id.advertisementListFragment,
                    inclusive = false
                )
            }
        }
    }

    private fun initGeminiResponseView(
        @StringRes message: Int,
        @ColorRes color: Int
    ) = with(binding) {
        llGeminiResponse.isVisible = true
        tvConfirmationStatus.text = getString(message)
        tvConfirmationStatus.isVisible = true
        tvConfirmationStatus.backgroundTintList = AppCompatResources.getColorStateList(
            requireContext(), color
        )
    }

    private fun getImagesFromCamera() {
        setFragmentResultListener(
            CameraFragment.IMAGE_LIST_REQUEST_KEY,
        ) { _, bundle ->
            val images = bundle.getStringArray(CameraFragment.IMAGE_LIST_BUNDLE_KEY)

            images?.let { list ->
                viewModel.updateImages(imageList = list.map { Uri.parse(it) })
            }
        }
    }

    private fun onPickImageClick() {
        if (isPickImagePermissionGranted()) {
            pickImageFromGallery.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            requestPickImagePermission.launch(REQUIRED_READ_IMAGE_PERMISSION)
        }
    }

    private fun showCameraFragment() {
        val isGranted = REQUIRED_CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (isGranted) {
            navigateToCameraFragment()
        } else {
            requestCameraPermissions.launch(REQUIRED_CAMERA_PERMISSIONS)
        }
    }

    private val pickImageFromGallery = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = Constants.MAX_IMAGES)
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.updateImages(imageList = uris)
        }
    }

    private val requestPickImagePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageFromGallery.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            showSnackbar(
                type = SnackbomType.ERROR,
                text = getString(R.string.text_permission_denied)
            )
        }
    }

    private val requestCameraPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        var permissionGranted = true

        permissions.entries.forEach {
            if (it.key in REQUIRED_CAMERA_PERMISSIONS && !it.value) permissionGranted = false
        }

        if (!permissionGranted) {
            showSnackbar(
                type = SnackbomType.ERROR,
                text = getString(R.string.text_permission_denied)
            )
        } else {
            navigateToCameraFragment()
        }
    }

    private fun navigateToCameraFragment() {
        findNavController().navigate(R.id.to_cameraFragment)
    }

    private fun isPickImagePermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), REQUIRED_READ_IMAGE_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val BOTTOM_SHEET_TAG = "IMAGE_SELECTION"

        private val REQUIRED_READ_IMAGE_PERMISSION =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }

        private val REQUIRED_CAMERA_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
