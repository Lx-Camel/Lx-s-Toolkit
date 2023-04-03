package com.lx.tools.base

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * @author liux
 * @date 2023/3/17
 * @desc
 */
abstract class BaseFragment : Fragment() {

    /**
     * WeChatFragment 根View
     */
    private lateinit var mRootView: View

    /**
     * 所有注册的view
     */
    private val mViewCache = SparseArray<View>()


    /**
     * 标识 WeChatFragment 是否为第一次创建View
     */
    private var mIsFirstCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIsFirstCreate = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::mRootView.isInitialized) {
            mRootView = inflater.inflate(onLayoutId(), container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mIsFirstCreate) {
            mIsFirstCreate = false
            onFindView(view)
            onBindViewValue(savedInstanceState)
            onBindViewListener()
            subscribeDataChanged()
        } else {
            onRestoreViewState(savedInstanceState)
        }
    }

    /**
     * 回调获取当前的布局文件ID
     *
     * @return 当前的布局文件Id
     */
    @androidx.annotation.LayoutRes
    protected abstract fun onLayoutId(): Int

    /**
     * 在此处执行 view 的获取
     *
     * @param rootView 根View
     */
    protected abstract fun onFindView(rootView: View)

    /**
     * 在此函数中对View进行属性赋值
     *
     * @param savedInstanceState 上次意外销毁时存储的一些数据
     */
    protected abstract fun onBindViewValue(savedInstanceState: Bundle?)

    /**
     * 在此函数中对View设置监听器
     */
    protected abstract fun onBindViewListener()

    /**
     * 在此处订阅数据变更
     */
    protected abstract fun subscribeDataChanged()

    /**
     * 恢复View 状态及数据
     *
     * @param savedInstanceState 上次意外销毁时存储的一些数据
     */
    protected open fun onRestoreViewState(savedInstanceState: Bundle?) {}

    /**
     * 根据id查找绑定view
     *
     * @param viewId 控件id
     * @param <E>    类型
     * @return view
     */
    protected open fun <E : View?> findView(@IdRes viewId: Int): E {
        var view: E = mViewCache.get(viewId) as E
        if (null == view) {
            view = mRootView.findViewById(viewId)
            mViewCache.put(viewId, view)
        }
        return view
    }

    fun getRootView(): View {
        return mRootView
    }
}