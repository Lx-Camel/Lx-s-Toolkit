package com.lx.tools.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author liux
 * @date 2023/3/17
 * @desc
 */
abstract class BaseViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    /**
     * 上下文
     */
    protected var mContext: Context? = null

    /**
     * 根布局
     */
    protected lateinit var mRootView: View


    init {
        loadAttribute(context, attrs)
        init(context, this)
    }

    /**
     * 加载自定义属性
     * @param context  [Context]
     * @param attrs {@linkAttributeSet }
     */
    protected open fun loadAttribute(context: Context, attrs: AttributeSet?) {}

    /**
     * 初始化
     *
     * @param context 上下文
     * @param parent  父布局
     */
    protected fun init(context: Context?, parent: BaseViewGroup?) {
        mContext = context
        mRootView = LayoutInflater.from(context).inflate(onLayoutId(), parent, true)
        onInitialize()
        onFindView()
        onBindViewValues()
        onBindViewListener()
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract fun onLayoutId(): Int

    /**
     * 关联控件
     */
    protected abstract fun onFindView()

    /**
     * 初始化
     */
    protected open fun onInitialize() {}

    /**
     * 设置view属性
     */
    protected abstract fun onBindViewValues()

    /**
     * 设置事件监听器
     */
    protected abstract fun onBindViewListener()

    /**
     * 当主题皮肤发生变更时回调
     */
    protected abstract fun onThemeSkinChanged()
}