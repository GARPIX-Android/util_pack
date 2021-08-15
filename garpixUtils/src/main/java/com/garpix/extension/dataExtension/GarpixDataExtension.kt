package com.garpix.extension.dataExtension

import java.util.Locale

/**
 * Метод для "очистки" номера телефона от дополнительных символов.
 * @return [String] возвращает номер без дополнительных символов.
 * @see <blockquote><font color="#000000">var</font>&nbsp;phone&nbsp;<font color="#339933">:</font>&nbsp;<font color="#003399">String</font>&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;+7(965)&nbsp;586-03-86&quot;</font><br/>
phone.<font color="#006633">rawPhoneNumber</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>&nbsp;<font color="#666666">//&nbsp;-&gt;&nbsp;+79655860386</font></blockquote>
 */
fun String.rawPhoneNumber(): String {
    return this.replace("(", "")
        .replace(")", "")
        .replace("-", "")
        .replace(" ", "")
}


/**
 * Метод для перевода строки в верхний регистр
 * @return строка в верхнем регистре
 * @see <blockquote><font color="#000000">var</font>&nbsp;test&nbsp;<font color="#339933">:</font>&nbsp;<font color="#003399">String</font>&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;test&quot;</font><br/>
test.<font color="#006633">capitalizeAllWords</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>&nbsp;<font color="#666666">//&nbsp;-&gt;&nbsp;TEST</font></blockquote>
 */
fun String.capitalizeAllWords(): String {
    val words = this.split("").toMutableList()
    var output = ""
    for (word in words) {
        output += word.uppercase(Locale.getDefault())
    }
    return output.trim()
}

/**
 * Метод для получения названия формата файла.
 * @return [String] Название файла
 * @see <blockquote><font color="#000000">var</font>&nbsp;file&nbsp;<font color="#339933">:</font>&nbsp;&nbsp;<font color="#003399">String</font>&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;test.png&quot;</font><br/>
file.<font color="#006633">getFileExtensionFromName</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>&nbsp;<font color="#666666">//&nbsp;-&gt;&nbsp;png</font></blockquote>
 */
fun String.getFileExtensionFromName(): String {
    var extension = ""
    val index: Int = this.lastIndexOf('.')
    if (index > 0) {
        extension = this.substring(index + 1)
    }
    return extension
}

/**
 * Метод для получения размера файла.
 * @return [String] Размер файла в МБ
 * @see <blockquote><font color="#000000">var</font>&nbsp;file&nbsp;<font color="#339933">:</font>&nbsp;&nbsp;<font color="#003399">String</font>&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;18000&quot;</font><br/>
file.<font color="#006633">sizeOfFile</font><font color="#009900">&#40;</font><font color="#009900">&#41;</font>&nbsp;<font color="#666666">//&nbsp;-&gt;&nbsp;18&nbsp;МБ</font></blockquote>
 */
fun String.sizeOfFile(): String {
    val size = this.toInt() / 1024
    return if (size < 1024)
        size.toString() + "КБ"
    else
        (size / 1024).toString() + "МБ"
}