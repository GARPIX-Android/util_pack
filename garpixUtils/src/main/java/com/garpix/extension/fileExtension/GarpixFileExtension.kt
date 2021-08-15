package com.garpix.extension.fileExtension

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Locale

/**
 * Метод для конвертировки [Uri] в [Bitmap]
 *
 * @param activity requireActivity()
 * @return Сконвертированный [Uri] в [Bitmap]
 * @see <blockquote><font color="#000000">val</font>&nbsp;result&nbsp;<font color="#339933">=</font>&nbsp;registerForActivityResult<font color="#009900">&#40;</font><br/>
ActivityResultContracts.<font color="#006633">GetContent</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>,&nbsp;<font color="#009900">&#123;</font>&nbsp;uri&nbsp;<font color="#339933">-&gt;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">val</font>&nbsp;bitmap<font color="#339933">:</font>&nbsp;Bitmap&nbsp;<font color="#339933">=</font>&nbsp;uri.<font color="#006633">toBitmap</font><font color="#009900">&#40;</font><font color="#000000">this</font><font color="#009900">&#41;</font><br/>
<font color="#009900">&#125;</font><font color="#009900">&#41;</font></blockquote>
 */
@RequiresApi(Build.VERSION_CODES.P)
fun Uri.toBitmap(activity: Activity): Bitmap {
    return when {
        Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
            activity.contentResolver,
            this
        )
        else -> {
            val source = ImageDecoder.createSource(activity.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
    }
}

/**
 * Метод для ковертировки [Uri] в [File]
 * @param context [Context]
 * @param uri [Uri]
 * @return Сконвертированный [Uri] в [File]
 * @see <blockquote><font color="#000000">val</font>&nbsp;result&nbsp;<font color="#339933">=</font>&nbsp;registerForActivityResult<font color="#009900">&#40;</font><br/>
ActivityResultContracts.<font color="#006633">GetContent</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>,&nbsp;<font color="#009900">&#123;</font>&nbsp;uri&nbsp;<font color="#339933">-&gt;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">val</font>&nbsp;file<font color="#339933">:</font>&nbsp;<font color="#003399">File</font>&nbsp;<font color="#339933">=</font>&nbsp;uri.<font color="#006633">getFile</font><font color="#009900">&#40;</font><font color="#000000">this</font><font color="#009900">&#41;</font><br/>
<font color="#009900">&#125;</font><font color="#009900">&#41;</font></blockquote>
 */
fun Uri.getFile(context: Context): File {
    val destinationFilename = File(context.filesDir.path + File.separatorChar + queryName(context, this))
    try {
        context.contentResolver.openInputStream(this).use { ins -> createFileFromStream(ins!!, destinationFilename) }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return destinationFilename
}

private fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

/**
 * Метод для получения названия формата файла.
 * @return [String] Название файла
 * @see <blockquote><font color="#000000">val</font>&nbsp;result&nbsp;<font color="#339933">=</font>&nbsp;registerForActivityResult<font color="#009900">&#40;</font><br/>
ActivityResultContracts.<font color="#006633">GetContent</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>,&nbsp;<font color="#009900">&#123;</font>&nbsp;uri&nbsp;<font color="#339933">-&gt;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">val</font>&nbsp;file<font color="#339933">:</font>&nbsp;<font color="#003399">File</font>&nbsp;<font color="#339933">=</font>&nbsp;uri.<font color="#006633">getFile</font><font color="#009900">&#40;</font><font color="#000000">this</font><font color="#009900">&#41;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="#000000">val</font>&nbsp;extension<font color="#339933">:</font>&nbsp;<font color="#003399">String</font>&nbsp;<font color="#339933">=</font>&nbsp;file.<font color="#006633">extension</font><br/>
<font color="#009900">&#125;</font><font color="#009900">&#41;</font></blockquote>
 */
val File.extension: String
    get() = name.substringAfterLast('.', "").uppercase(Locale.getDefault())

fun File.size(): String {
    val size = length() / 1024
    return if (size < 1024)
        size.toString() + "КБ"
    else
        (size / 1024).toString() + "МБ"
}