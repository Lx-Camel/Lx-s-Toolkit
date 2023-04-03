package com.lx.tools

import android.content.Context
import android.content.SharedPreferences

/**
 * @author liux
 * @date 2023/3/23
 * @desc SharedPreferences存储工具类
 */
class SPUtil {
    private val sp: SharedPreferences = Util.app.getSharedPreferences("", Context.MODE_PRIVATE)
    private val editor = sp.edit()

    companion object {
        val instance: SPUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SPUtil()
        }
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String): String {
        return sp.getString(key, "")!!
    }
}