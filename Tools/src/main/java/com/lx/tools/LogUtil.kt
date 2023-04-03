package com.lx.tools

import android.util.Log

/**
 * @author liux
 * @date 2022/10/11
 * @desc Log工具类
 */


//共同包含TAG
private var TAG = "Liux"

//打印调试开关,默认打印Log
private var IS_DEBUG = true

//Log 单词打印的最大长度
private const val MAX_LENGTH = 3 * 1024

fun init(commonTag: String? = null) {
    commonTag?.run { TAG = this }
}

fun openLog() {
    IS_DEBUG = true
}

fun closeLog() {
    IS_DEBUG = false
}

/**
 * 获取 TAG 信息：文件名以及行数
 *
 * @return TAG 信息
 */
@Synchronized
private fun getTAG(): String {
    val tag = StringBuilder()
    val sts = Thread.currentThread().stackTrace ?: return ""
    for (st in sts) {
        //筛选获取需要打印的TAG
        if (!st.isNativeMethod && st.className != Thread::class.java.name) {//&& st.className != LogUtil::class.java.name
            //获取文件名以及打印的行数
            tag.append("(").append(st.fileName).append(":").append(st.lineNumber).append(")")
            return TAG + "_" + tag.toString()
        }
    }
    return TAG
}

/**
 * Log.e 打印
 *
 * @param text 需要打印的内容
 */
@Synchronized
fun logE(text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.e(getTAG(), this)
            }
        }
    }
}

/**
 * Log.e 打印
 *
 * @param tag 可以用来表示某一类业务
 * @param text 需要打印的内容
 */
@Synchronized
fun logE(tag: String, text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.e(getTAG() + "_" + tag, this)
            }
        }
    }
}

/**
 * Log.e 打印
 *
 * @param text 需要打印的内容
 */
@Synchronized
fun logE(text: String, e: Exception) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.e(getTAG(), this, e)
            }
        }
    }
}

/**
 * Log.e 打印
 *
 * @param tag 可以用来表示某一类业务
 * @param text 需要打印的内容
 */
@Synchronized
fun logE(tag: String, text: String, e: Exception) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.e(getTAG() + "_" + tag, this, e)
            }
        }
    }
}

/**
 * Log.d 打印
 *
 * @param text 需要打印的内容
 */
@Synchronized
fun logD(text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.d(getTAG(), this)
            }
        }
    }
}

/**
 * Log.d 打印
 *
 * @param tag 可以用来表示某一类业务
 * @param text 需要打印的内容
 */
@Synchronized
fun logD(tag: String, text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.d(getTAG() + "_" + tag, this)
            }
        }
    }
}

/**
 * Log.w 打印
 *
 * @param text 需要打印的内容
 */
@Synchronized
fun logW(text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.w(getTAG(), this)
            }
        }
    }
}

/**
 * Log.w 打印
 *
 * @param tag 可以用来表示某一类业务
 * @param text 需要打印的内容
 */
@Synchronized
fun logW(tag: String, text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.w(getTAG() + "_" + tag, this)
            }
        }
    }
}

/**
 * Log.i 打印
 *
 * @param text 需要打印的内容
 */
@Synchronized
fun logI(text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.i(getTAG(), this)
            }
        }
    }
}

/**
 * Log.i 打印
 *
 * @param tag 可以用来表示某一类业务
 * @param text 需要打印的内容
 */
@Synchronized
fun logI(tag: String, text: String) {
    if (IS_DEBUG) {
        for (str in splitStr(text)) {
            str?.run {
                Log.i(getTAG() + "_" + tag, this)
            }
        }
    }
}

/**
 * Log.e 打印格式化后的JSON数据
 *
 * @param json 需要打印的内容
 */
@Synchronized
fun logJson(json: String) {
    if (IS_DEBUG) {
        try {
            //转化后的数据
            val logStr = formatJson(json)
            for (str in splitStr(logStr)) {
                str?.run {
                    Log.e(getTAG(), this)
                }
            }
        } catch (e: Exception) {
            Log.e(getTAG(), e.toString(), e)
        }
    }
}

/**
 * Log.e 打印格式化后的JSON数据
 *
 * @param tag 可以用来表示某一类业务
 * @param json 需要打印的内容
 */
@Synchronized
fun logJson(tag: String, json: String) {
    if (IS_DEBUG) {
        try {
            //转化后的数据
            val logStr = formatJson(json)
            for (str in splitStr(logStr)) {
                str?.run {
                    Log.e(getTAG() + "_" + tag, this)
                }
            }
        } catch (e: Exception) {
            Log.e(getTAG() + "_" + tag, e.toString(), e)
        }
    }
}

/**
 * 数据分割成不超过 MAX_LENGTH的数据
 *
 * @param str 需要分割的数据
 * @return 分割后的数组
 */
private fun splitStr(str: String): Array<String?> {
    //字符串长度
    val length = str.length
    //返回的数组
    val strs = arrayOfNulls<String>(length / MAX_LENGTH + 1)
    //
    var start = 0
    for (i in strs.indices) {
        //判断是否达到最大长度
        if (start + MAX_LENGTH < length) {
            strs[i] = str.substring(start, start + MAX_LENGTH)
            start += MAX_LENGTH
        } else {
            strs[i] = str.substring(start, length)
            start = length
        }
    }
    return strs
}


/**
 * 格式化
 *
 * @param jsonStr json数据
 * @return 格式化后的json数据
 */
private fun formatJson(jsonStr: String?): String {
    if (null == jsonStr || "" == jsonStr) return ""
    val sb = StringBuilder()
    var last = '\u0000'
    var current = '\u0000'
    var indent = 0
    var isInQuotationMarks = false
    for (i in 0 until jsonStr.length) {
        last = current
        current = jsonStr[i]
        when (current) {
            '"' -> {
                if (last != '\\') {
                    isInQuotationMarks = !isInQuotationMarks
                }
                sb.append(current)
            }
            '{', '[' -> {
                sb.append(current)
                if (!isInQuotationMarks) {
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
            }
            '}', ']' -> {
                if (!isInQuotationMarks) {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                }
                sb.append(current)
            }
            ',' -> {
                sb.append(current)
                if (last != '\\' && !isInQuotationMarks) {
                    sb.append('\n')
                    addIndentBlank(sb, indent)
                }
            }
            else -> sb.append(current)
        }
    }
    return sb.toString()
}

/**
 * 在 StringBuilder指定位置添加 space
 *
 * @param sb     字符集
 * @param indent 添加位置
 */
private fun addIndentBlank(sb: StringBuilder, indent: Int) {
    for (i in 0 until indent) {
        sb.append('\t')
    }
}
