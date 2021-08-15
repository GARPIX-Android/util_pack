package com.garpix.extension.validator

import com.garpix.extension.units.GarpixDateUnit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

/**
 * Валидация строки на на дату. В параметры один из DateUnit
 * @param pattern
 * @return return matching string to [pattern]
 * @see <blockquote><font color="#000000">val</font>&nbsp;date&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;12.12.2011&quot;</font><br/>
<font color="#000000">if</font>&nbsp;<font color="#009900">&#40;</font>date.<font color="#006633">isValidDate</font><font color="#009900">&#40;</font>GarpixDateUnit.<font color="#006633">PATTERN_1</font><font color="#009900">&#41;</font><font color="#009900">&#41;</font><font color="#009900">&#123;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="#666666">//&nbsp;TODO</font><br/>
<font color="#009900">&#125;</font></blockquote>
 */
fun String.isValidDate(pattern: GarpixDateUnit): Boolean {
    val dateFormat = SimpleDateFormat(
        pattern.value, Locale.getDefault()
    )
    dateFormat.isLenient = false
    return try {
        dateFormat.parse(this)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Форматирование одного паттерна даты, в другой.
 * @param from Конвертируемый паттерн [GarpixDateUnit]
 * @param to Итоговый паттерн [GarpixDateUnit]
 * @return Сконвертированная дата в другом паттерне
 * @see <blockquote><font color="#000000">val</font>&nbsp;date&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;12.12.2011&quot;</font><br/>
date.<font color="#006633">formatDate</font><font color="#009900">&#40;</font>from&nbsp;<font color="#339933">=</font>&nbsp;GarpixDateUnit.<font color="#006633">PATTERN_1</font>,&nbsp;to&nbsp;<font color="#339933">=</font>&nbsp;GarpixDateUnit.<font color="#006633">PATTERN_3</font><font color="#009900">&#41;</font></blockquote>
 */
fun String.formatDate(from: GarpixDateUnit, to: GarpixDateUnit): String {
    return try {
        val russian = Locale("ru")

        val dateFormat = SimpleDateFormat(
            from.value, russian
        )
        val date = dateFormat.parse(this)
        val dateFormat2 = SimpleDateFormat(
            to.value, russian
        )
        dateFormat2.format(date!!).toString()
    } catch (e: Exception) {
        e.message!!
    }
}

/**
 * Валидация строки по [Regex] на корректный формат электронной почты.
 *
 * @return [Boolean] корректность электронной почты.
 */
fun String.isEmailValid(): Boolean {
    return (Pattern.compile(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(this).matches())
}

/**
 * Метод конвертации [String] в [Date]
 *
 * @param to Итоговый паттерн [GarpixDateUnit]
 * @return [Date]
 */
fun String.toData(to: GarpixDateUnit): Date {
    val dateFormat = SimpleDateFormat(to.value, Locale.getDefault())
    return try {
        dateFormat.parse(this)!!
    } catch (e: Exception) {
        Date()
    }
}