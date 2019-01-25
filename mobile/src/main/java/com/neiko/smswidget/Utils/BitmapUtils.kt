package com.neiko.smswidget.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.android.gms.common.util.IOUtils.toByteArray
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream


class BitmapUtils {

    fun fromBase64(row: String): Bitmap {
        val decodedString = Base64.decode(row, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }

    fun toBase64(image: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}