package com.lx.tools.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

/**
 * @author liux
 * @date 2023/3/17
 * @desc 页面跳转工具类
 */
abstract class PageFragment : BaseFragment() {

    /**
     * 跳转到某个Fragment
     *
     * @param resId action 或 destination
     */
    protected open fun go(@IdRes resId: Int) {
        go(resId, null)
    }

    /**
     * 跳转到某个Fragment
     *
     * @param resId action 或 destination
     * @param args  参数
     */
    protected open fun go(@IdRes resId: Int, args: Bundle?) {
        getNavigationController()?.navigate(resId, args)
    }

    /**
     * 回退页面
     */
    protected open fun back() {
        getNavigationController()?.navigateUp()
    }


    /**
     * 返回到某个页面
     *
     * @param destId 目标页面id
     */
    protected open fun popTo(@IdRes destId: Int): Boolean {
        return popTo(destId, false)
    }

    /**
     * 返回到某个页面
     *
     * @param destId    目标页面id
     * @param inclusive 目标页面是否出栈
     * @return 执行结果
     */
    protected open fun popTo(@IdRes destId: Int, inclusive: Boolean): Boolean {
        return getNavigationController()?.popBackStack(destId, inclusive) ?: false
    }

    /**
     * 监听livedata数据
     *
     * @param data     livedata数据
     * @param observer 监听器
     * @param <T>      livedata中的泛型
    </T> */
    protected open fun <T> observerLiveData(data: LiveData<T>, observer: Observer<in T>) {
        data.observe(this.viewLifecycleOwner, observer)
    }

    fun getNavigationController(): NavController? {
        try {
            return Navigation.findNavController(getRootView())
        } catch (ignored: Throwable) {
        }
        return null
    }
}