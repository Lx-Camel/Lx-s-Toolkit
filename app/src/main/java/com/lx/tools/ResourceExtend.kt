package com.lx.tools

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat

/**
 * @author liux
 * @date 2022/10/11
 * @desc 资源获取扩展工具
 */
/**
 * 通过id获取字符串资源
 */
val @receiver:StringRes Int.stringResource: String
    get() = Util.app.resources.getString(this)

/**
 * 通过id获取字符串数组资源
 */
val @receiver:ArrayRes Int.stringArrayResource: Array<String>
    get() = Util.app.resources.getStringArray(this)

/**
 * 通过id获取格式化字符串资源
 */
fun @receiver:StringRes Int.stringResource(vararg formats: Any?): String =
    Util.app.resources.getString(this, *formats)

/**
 * 通过id获取颜色资源
 */
val @receiver:ColorRes Int.colorResource
    get() = ResourcesCompat.getColor(Util.app.resources, this, null)

/**
 * 通过id获取字体资源
 */
val @receiver:FontRes Int.fontResource
    get() = ResourcesCompat.getFont(Util.app, this)

/**
 * 通过id获取图片资源
 */
val @receiver:DrawableRes Int.drawableResource
    @SuppressLint("UseCompatLoadingForDrawables")
    get() = Util.app.getDrawable(this)

/**
 * 通过id获取图片资源
 */
fun @receiver:DrawableRes Int.toBitmap(): Bitmap? =
    when (val drawable = this.drawableResource) {
        is BitmapDrawable -> drawable.bitmap
        is VectorDrawable -> {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(Canvas(bitmap))
            bitmap
        }
        else -> {
            logE("未知类型")
            null
        }
    }