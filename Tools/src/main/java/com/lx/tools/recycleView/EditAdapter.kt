package com.lx.tools.recycleView


/**
 * @author liux
 * @date 2023/3/2
 * @desc 可编辑列表Adapter
 */
class EditAdapter<T> @JvmOverloads constructor(
    resLayout: Int,
    clazz: Class<out EditViewHolder<T>>,
    clazzDiff: Class<out BaseItemDiffCallBack<T>>? = null
) : BaseAdapter<T>(resLayout, clazz, clazzDiff) {

    private var isEdit = false
    private val checkList: NoRepeatArrayList<Int> = NoRepeatArrayList()
    private var allCheckListener: AllCheckListener? = null

    fun registerAllCheckListener(listener: AllCheckListener) {
        allCheckListener = listener
    }

    fun editState() {
        isEdit = true
        notifyDataSetChanged()
    }

    fun defaultState() {
        isEdit = false
        checkList.clear()
        notifyDataSetChanged()
    }

    fun checkAll(b: Boolean) {
        if (b) {
            for (i in 0 until mList.size) {
                mList[i]?.run { checkList.noRepeatAdd(i) }
            }
        } else checkList.clear()
        notifyDataSetChanged()
    }

    fun isEdit(): Boolean {
        return isEdit
    }

    fun getCheckList(): ArrayList<Int> {
        return checkList
    }

    override fun bindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        if (holder is EditViewHolder) {
            with(holder) {
                itemView.setOnClickListener {
                    mItemClickListener?.onClick(position, mList[position])
                }
                change(isEdit)
                registerCheckedChangeListener { position, isChecked ->
                    if (isChecked) checkList.noRepeatAdd(position)
                    else checkList.noRepeatRemove(position)
                    var num = 0
                    mList.forEach { it?.run { num++ } }
                    allCheckListener?.onCheckedChanged(checkList.size == num)
                }
                bindViewHolder(mList, position, checkList.contains(position))
            }
        }
    }

    interface EditChangeListener {
        fun change(isEdit: Boolean)
    }

    fun interface CheckedChangeListener {
        fun onCheckedChanged(position: Int, isChecked: Boolean)
    }

    //对item导致maincheckbox改变做监听
    interface AllCheckListener {
        fun onCheckedChanged(b: Boolean)
    }

    class NoRepeatArrayList<T> : ArrayList<T>() {

        fun noRepeatAdd(element: T) {
            if (!contains(element)) {
                add(element)
            }
        }

        fun noRepeatRemove(element: T) {
            if (contains(element)) {
                remove(element)
            }
        }
    }
}