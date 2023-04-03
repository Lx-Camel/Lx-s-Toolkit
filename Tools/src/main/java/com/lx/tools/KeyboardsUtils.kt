package com.lx.tools

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*

/**
 * @author liux
 * @date 2022/10/11
 * @desc 键盘控制工具类
 */
object KeyboardsUtils {

    /**
     * 显示软键盘
     * @param view
     */
    fun showKeyboard(view: View?) {
        logI("showKeyboard view:$view")
        val inputManager =
            view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, 0)
    }

    /**
     * 延时显示软键盘
     * @param view
     * @desc 布局加载时无法弹出软键盘，所以稍微延时弹出
     */
    fun showKeyboardDelayed(view: View?, time: Long = 50) {
        logI("showKeyboardDelayed view:$view")
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val imm =
                    view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }, time)
    }

    /**
     * 隐藏软键盘
     * @param view
     */
    fun hintKeyBoards(view: View?) {
        logI("hintKeyBoards view:$view")
        val imm = view?.context
            ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
        }
    }

    /**
     * 判定当前是否需要隐藏
     */
    fun isShouldHideKeyBord(v: View?, ev: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l);
            val left = l[0];
            val top = l[1];
            val bottom = top + v.getHeight();
            val right = left + v.getWidth();
            return !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom);
        }
        return false;
    }
}