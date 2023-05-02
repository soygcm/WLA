package com.wla.petfeeder.capture

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.camera.
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.wla.petfeeder.R
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream

@RequiresApi(Build.VERSION_CODES.R)
fun takePhoto(context: Context, lifecycleOwner: LifecycleOwner) {

    // Crear un archivo temporal para guardar la foto
    val outputDirectory = getOutputDirectory(context)
    val photoFile = File(outputDirectory, "photo.jpg")

    // Crear un objeto para configurar la captura de fotos
    val imageCapture = context.display?.let {
        ImageCapture.Builder()
        .setTargetRotation(it.rotation)
        .build()
    }

    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)
    val processCameraProvider = cameraProviderFuture.get()

    processCameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_FRONT_CAMERA, imageCapture)


    // Configurar el Executor para el listener de captura de fotos
    val executor = ContextCompat.getMainExecutor(context)

    Log.d("TakePhoto", imageCapture.toString())
    // Definir el listener de captura de fotos
    imageCapture?.takePicture(ImageCapture.OutputFileOptions.Builder(photoFile).build(), executor, object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            // Guardar la foto en la galería del dispositivo
            savePhotoToGallery(context, photoFile)
        }

        override fun onError(exception: ImageCaptureException) {
            // Manejar errores de captura de fotos
            Log.d("TakePhoto", "error $exception")

        }
    })
}

private fun getOutputDirectory(context: Context): File {
    val appContext = context.applicationContext
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else appContext.filesDir
}

private fun savePhotoToGallery(context: Context, photoFile: File) {
    Log.d("TakePhoto", photoFile.toString())

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "photo.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val contentResolver = context.contentResolver
    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let {
        contentResolver.openOutputStream(uri)?.use { outputStream ->
            val inputStream = FileInputStream(photoFile)
            IOUtils.copy(inputStream, outputStream)
            inputStream.close()
        }
    }
}