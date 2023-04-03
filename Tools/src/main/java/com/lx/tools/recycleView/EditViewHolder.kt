package com.lx.tools.recycleView

import android.view.View

/**
 * @author liux
 * @date 2023/3/7
 * @desc 可编辑列表ViewHolder
 */
abstract class EditViewHolder<T>(itemView: View) : BaseViewHolder<T>(itemView),
    EditAdapter.EditChangeListener {

    protected var mCheckedChangeListener: EditAdapter.CheckedChangeListener? = null

    open fun bindViewHolder(
        list: MutableList<T>,
        position: Int,
        isCheck: Boolean
    ) {
        mData = list
        mPosition = position
    }

    fun registerCheckedChangeListener(listener: EditAdapter.CheckedChangeListener) {
        mCheckedChangeListener = listener
    }
}