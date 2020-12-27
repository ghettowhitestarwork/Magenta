package com.ghettowhitestar.magentatest.source

import android.graphics.Bitmap
import android.os.Environment
import com.ghettowhitestar.magentatest.data.PicsumPhoto
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CacheManager {
    /**
     * Удаление фотографии из памяти телефона
     * @ path путь к сохраненной фотографии
     */
    fun deleteImage(path: String) {
        val storageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/DCIM"
        )
        val imageFile = File(storageDir, path)
        if (imageFile.exists()) {
            imageFile.delete()
        }
    }

    /** Сохранение фотографии на телефоне */
    fun saveImage(image: Bitmap, photo: PicsumPhoto) {
        val storageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/DCIM"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, photo.path)
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 30, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}