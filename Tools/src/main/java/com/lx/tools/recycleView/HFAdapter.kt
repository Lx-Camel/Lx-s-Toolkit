package com.lx.tools.recycleView

import android.annotation.SuppressLint
import com.lx.tools.logI

/**
 * @author liux
 * @date 2022/7/5
 * @desc 带有头尾的列表Adapter
 */
open class HFAdapter<T> @JvmOverloads constructor(
    resLayout: Int,
    clazz: Class<out BaseViewHolder<T>>,
    layoutMap: MutableMap<Int, Int>? = null,
    clazzDiff: Class<out BaseItemDiffCallBack<T>>? = null
) : BaseAdapter<T>(resLayout, clazz, clazzDiff) {

    private var mHeaderData: Any? = null
    private var mFooterData: Any? = null
    val mLayoutMap = layoutMap

    override fun getLayout(viewType: Int): Int {
        var layout = mResLayout
        when (viewType) {
            TYPE_HEADER -> if (checkMapHeader()) layout = mLayoutMap!![TYPE_HEADER]!!
            TYPE_FOOTER -> if (checkMapFooter()) layout = mLayoutMap!![TYPE_FOOTER]!!
        }
        return layout
    }

    override fun getItemCount(): Int {
        return mList.size + getSpecialItemNum()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 && checkMapHeader() -> TYPE_HEADER
            position == (itemCount - 1) && checkMapFooter() -> TYPE_FOOTER
            else -> 0
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemView.setOnClickListener {
            mItemClickListener?.let {
                when {
                    position == 0 && checkMapHeader() ->
                        if (it is HFItemClickListener) it.onClickHeader(mHeaderData)
                    position == (itemCount - 1) && checkMapFooter() ->
                        if (it is HFItemClickListener) it.onClickFooter(mFooterData)
                    else ->
                        if (checkMapHeader()) it.onClick(position, mList[position - 1])
                        else it.onClick(position, mList[position])
                }
                clickPosition = position
                notifyDataSetChanged()
            }
        }
        val isSelect = position == clickPosition
        when {
            position == 0 && checkMapHeader() -> holder.bindHeader(
                mHeaderData,
                mList,
                position,
                isSelect
            )
            position == (itemCount - 1) && checkMapFooter() -> holder.bindFooter(
                mFooterData,
                mList,
                position,
                isSelect
            )
            else -> if (checkMapHeader()) holder.bindViewHolder(
                mList,
                position - 1,
                payloads,
                isSelect
            ) else holder.bindViewHolder(mList, position, payloads, isSelect)
        }
    }

    fun setHeaderData(data: Any) {
        mHeaderData = data
    }

    fun setFooterData(data: Any) {
        mFooterData = data
    }

    private fun checkMapHeader(): Boolean {
        return mLayoutMap?.containsKey(TYPE_HEADER) ?: false
    }

    private fun checkMapFooter(): Boolean {
        return mLayoutMap?.containsKey(TYPE_FOOTER) ?: false
    }

    private fun getSpecialItemNum(): Int {
        var num = 0
        if (checkMapHeader()) num++
        if (checkMapFooter()) num++
        return num
    }
}

const val TYPE_HEADER = -1
const val TYPE_FOOTER = -2

interface HFItemClickListener : BaseAdapter.ItemClickListener {
    fun onClickHeader(bean: Any? = null) {
        logI("onClickHeader")
    }

    fun onClickFooter(bean: Any? = null) {
        logI("onClickFooter")
    }
}

fun layoutMapOf(vararg pairs: Pair<Int, Int>): MutableMap<Int, Int> =
    HashMap<Int, Int>().apply {
        for ((key, value) in pairs) {
            this[key] = value
        }
    }