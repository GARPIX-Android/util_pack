package com.garpix.extension.viewExtension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.doOnDetach

/**
 * Метод для короткого отображения [Toast]
 *
 * @param text Текст для отображения
 */
fun Context.showShortToast(text: String) {
    Toast.makeText(this, text, LENGTH_SHORT).show()
}

/**
 * Метод для продолжительного отображения [Toast]
 * @param text Текст для отображения
 */
fun Context.showLongToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Метод для блокировки кнопки после нажатия на определенное время
 * @param ml Миллисекунды "блокировки" кнопки после нажатия, Значение по умолчанию - 1 сек
 * @param callback
 */
fun View.setThrottleOnClickListener(ml: Long, callback: (view: View) -> Unit) {
    var lastClickTime = 0L
    this.setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > ml) {
            lastClickTime = currentTimeMillis
            callback.invoke(it)
        }
    }
}

/**
 * Метод для отображения клавиатуры
 *
 */
fun View.focusAndShowKeyboard() {
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
    requestFocus()
    if (hasWindowFocus()) {
        showTheKeyboardNow()
    } else {
        val listener = ViewTreeObserver.OnWindowFocusChangeListener { hasFocus ->
            if (hasFocus) showTheKeyboardNow()
        }
        viewTreeObserver.addOnWindowFocusChangeListener(listener)

        doOnDetach { viewTreeObserver.removeOnWindowFocusChangeListener(listener) }
    }
}

/**
 * Метод для скрытия клавиатуры
 *
 */
fun Activity.hideKeyboard() {
    currentFocus?.hideKeyboard()
}

/**
 * Метод для скрытия клавиатуры
 *
 */
fun View.hideKeyboard() {
    post {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }
}