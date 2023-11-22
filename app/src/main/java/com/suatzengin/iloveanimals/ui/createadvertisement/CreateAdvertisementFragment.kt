package com.suatzengin.iloveanimals.ui.createadvertisement

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suatzengin.iloveanimals.R
import com.suatzengin.iloveanimals.core.ui.MarginItemDecoration
import com.suatzengin.iloveanimals.core.ui.snackbar.SnackbomType
import com.suatzengin.iloveanimals.core.viewbinding.viewBinding
import com.suatzengin.iloveanimals.databinding.FragmentCreateAdvertisementBinding
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.ui.createadvertisement.adapter.ImageAdapter
import com.suatzengin.iloveanimals.ui.imageselection.CameraBottomSheet
import com.suatzengin.iloveanimals.ui.imageselection.ImageSelectionBottomSheet
import com.suatzengin.iloveanimals.util.extension.showSnackbar

class CreateAdvertisementFragment : Fragment(R.layout.fragment_create_advertisement) {
    private val binding by viewBinding(FragmentCreateAdvertisementBinding::bind)

    private val imageAdapter: ImageAdapter by lazy { ImageAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupRecyclerView()
    }

    private fun initView() = with(binding) {
        toolbar.setOnLeftIconClick {
            findNavController().navigateUp()
        }

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            AdvertisementCategory.entries.takeLast(3).map {
                AdvertisementCategory.getTitle(it)
            }
        )

        etCategory.setAdapter(arrayAdapter)

        etCategory.setOnItemClickListener { _, _, position, _ ->
            val item = arrayAdapter.getItem(position)
            val category = AdvertisementCategory.getWithTitle(item.orEmpty())

            showSnackbar(
                type = SnackbomType.INFO,
                "category: $category"
            )
        }

        layoutSelectImage.setOnClickListener {
            val bottomSheet = ImageSelectionBottomSheet(
                onTakePictureClick = {
                    showCameraBottomSheet()

                },
                onPickImageFromGalleryClick = ::onPickImageClick
            )

            bottomSheet.show(childFragmentManager, BOTTOM_SHEET_TAG)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvImages

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        recyclerView.addItemDecoration(MarginItemDecoration(left = 8, right = 8))
        recyclerView.adapter = imageAdapter
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

    private fun showCameraBottomSheet() {
        val isGranted = REQUIRED_CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (isGranted) {
            val camera = CameraBottomSheet()

            camera.show(childFragmentManager, "Camera")
        } else {
            requestCameraPermissions.launch(REQUIRED_CAMERA_PERMISSIONS)
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
            showSnackbar(type = SnackbomType.ERROR, text = "İzin reddedildi!")
        } else {
            val camera = CameraBottomSheet()

            camera.show(childFragmentManager, "Camera")
        }
    }

    private fun isPickImagePermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), REQUIRED_READ_IMAGE_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPickImagePermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageFromGallery.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            showSnackbar(type = SnackbomType.ERROR, text = "İzin reddedildi!")
        }
    }

    private val pickImageFromGallery = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")

            imageAdapter.submitList(uris)

            uris.forEach {
                println("uri: $it")
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
            showSnackbar(type = SnackbomType.INFO, text = "Herhangi bir resim seçilmedi!")
        }
    }

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
