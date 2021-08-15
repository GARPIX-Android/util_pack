package com.garpix.extension.resourceExtension

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * @param context
 * @param vectorResId
 * @return Bitmap icon
 */
private fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        vectorDrawable!!.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * @param context [Context]
 * @param position Долготоа и широта [LatLng]
 * @param title Имя маркера
 * @param icon Иконка маркера
 * @throws [NotFoundException]
 * @see <blockquote>googleMap.<font color="#006633">addCustomMarker</font><font color="#009900">&#40;</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;context&nbsp;<font color="#339933">=</font>&nbsp;<font color="#000000">this</font>,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;position&nbsp;<font color="#339933">=</font>&nbsp;LatLng<font color="#009900">&#40;</font><font color="#cc66cc">42.846051819138594</font>,&nbsp;<font color="#cc66cc">74.59891706952783</font><font color="#009900">&#41;</font>,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;title&nbsp;<font color="#339933">=</font>&nbsp;<font color="#0000ff">&quot;Location&quot;</font>,<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;icon&nbsp;<font color="#339933">=</font>&nbsp;R.<font color="#006633">drawable</font>.<font color="#006633">some_icon</font><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#009900">&#41;</font></blockquote>
 */
fun GoogleMap.addCustomMarker(context: Context, position: LatLng, title: String, icon: Int) {
    this.addMarker(
        MarkerOptions()
            .position(position)
            .title(title)
            .icon(bitmapDescriptorFromVector(context, icon))
    )
}

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
