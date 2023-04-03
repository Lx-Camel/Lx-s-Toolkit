package com.lx.tools.recycleView

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author liux
 * @date 2022/6/30
 * @desc 基础ViewHolder
 */
open class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var mData: List<T>

    var mPosition = 0

    protected var mViewClickListener: BaseAdapter.ViewClickListener? = null

    open fun bindViewHolder(
        list: MutableList<T>,
        position: Int,
        payloads: MutableList<Any>?,
        isSelect: Boolean
    ) {
        mData = list
        mPosition = position
    }

    open fun bindHeader(
        headerData: Any?,
        list: MutableList<T>,
        position: Int,
        isSelect: Boolean
    ) {
        mPosition = position
    }

    open fun bindFooter(
        footerData: Any?,
        list: MutableList<T>,
        position: Int,
        isSelect: Boolean
    ) {
        mPosition = position
    }

    open fun registerViewClickListener(listener: BaseAdapter.ViewClickListener) {
        mViewClickListener = listener
    }
}