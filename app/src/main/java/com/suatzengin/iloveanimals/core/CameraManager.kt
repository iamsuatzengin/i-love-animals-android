package com.suatzengin.iloveanimals.core

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.Locale

class CameraManager(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) {
    private var imageCapture: ImageCapture? = null

    fun startCamera(
        surfaceProvider: SurfaceProvider,
        cameraSelector: CameraSelector
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also { prev ->
                    prev.setSurfaceProvider(surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            runCatching {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            }.onFailure { throwable ->
                Log.e("CameraError", "Bir hata oluştu!", throwable)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(onPhotoCaptured: (Uri?) -> Unit) {
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, photoName)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onPhotoCaptured(outputFileResults.savedUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Camera", "Photo capture failed: ${exception.message}", exception)
                }
            }
        )
    }

    companion object {
        private const val PHOTO_NAME_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val MIME_TYPE = "image/jpeg"

        private val photoName = SimpleDateFormat(
            PHOTO_NAME_DATE_FORMAT,
            Locale.forLanguageTag("tr")
        ).run {
            format(System.currentTimeMillis())
        }
    }
}

fun Fragment.cameraManager() = CameraManager(
    context = requireContext(),
    lifecycleOwner = viewLifecycleOwner
)
