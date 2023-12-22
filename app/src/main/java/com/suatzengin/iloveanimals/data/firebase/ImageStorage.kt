package com.suatzengin.iloveanimals.data.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.suatzengin.iloveanimals.di.dispatcher.Dispatcher
import com.suatzengin.iloveanimals.di.dispatcher.IlaDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

private const val PROGRESS_MULTIPLIER = 100.0

class ImageStorage @Inject constructor(
    private val storage: FirebaseStorage,
    @Dispatcher(IlaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    private val imageReference: StorageReference
        get() = storage.reference

    suspend fun uploadImage(uris: List<Uri>): List<String> = withContext(ioDispatcher) {
        runCatching {
            val uri = uris.map {
                val ref = imageReference.child("images/${it.lastPathSegment}-${UUID.randomUUID()}")

                ref.putFile(it)
                    .addOnProgressListener { task ->
                        val bytesTransferred = task.bytesTransferred
                        val totalByteCount = task.totalByteCount

                        val progress = (PROGRESS_MULTIPLIER * bytesTransferred) / totalByteCount
                        Log.d("TAG", "${it.lastPathSegment} - Upload is $progress% done")
                    }
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }

            uri.map { it.toString() }
        }.onFailure {
            throw ImageUploadingException(it.message ?: "Resim yüklenirken bir hata oluştu")
        }.getOrElse { emptyList() }
    }
}

internal class ImageUploadingException(message: String? = null) : Exception(message)
