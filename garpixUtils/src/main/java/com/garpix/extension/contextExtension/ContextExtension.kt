package com.garpix.extension.contextExtension

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * Метод для сохранения текстового контента в буффер обмена.
 *
 * @param content текст который копируем
 * @param appName название приложения (R.string.app_name)
 * @see <blockquote>copyToClipBoard<font color="#009900">&#40;</font>content&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;www.google.com&quot;</font>,&nbsp;appName<font color="#339933">:</font>&nbsp;<font color="#0000ff">&quot;AppName&quot;</font><font color="#009900">&#41;</font></blockquote>
 */
fun Context.copyToClipBoard(content: String, appName: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(appName, content)
    clipboard.setPrimaryClip(clip)
}

/**
 *  Метод для вызова вибрации.
 *
 * @param duration продолжительность вибрации в миллисекундах. Значение по умолчанию - 0.5 сек.
 * @see <blockquote>vibrate<font color="#009900">&#40;</font>duration&nbsp;<font color="#339933">=</font>&nbsp;<font color="#cc66cc">1000</font><font color="#009900">&#41;</font></blockquote>
 */
@SuppressLint("MissingPermission")
fun Context.vibrate(duration: Long = 500) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(
            duration,
            VibrationEffect.DEFAULT_AMPLITUDE
        )
        vibrator?.vibrate(effect)
    } else {
        vibrator?.vibrate(duration)
    }
}