package com.lx.tools.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor

/**
 * @author liux
 * @date 2022/6/30
 * @desc 基础Adapter
 */
open class BaseAdapter<T> @JvmOverloads constructor(
    resLayout: Int,
    clazz: Class<out BaseViewHolder<T>>,
    clazzDiff: Class<out BaseItemDiffCallBack<T>>? = null,
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var mList: MutableList<T> = ArrayList()
    val mResLayout = resLayout
    val mClazz = clazz
    var mClazzDiff = clazzDiff
    var mItemClickListener: ItemClickListener? = null
    var mViewClickListener: ViewClickListener? = null

    var clickPosition = 0

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun setViewClickListener(viewClickListener: ViewClickListener) {
        mViewClickListener = viewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(getLayout(viewType), parent, false)

        val mClazzConstructor: Constructor<out BaseViewHolder<T>> =
            mClazz.getConstructor(
                View::class.java
            )
        val holder: BaseViewHolder<T> = mClazzConstructor.newInstance(view)
        mViewClickListener?.also {
            holder.registerViewClickListener(it)
        }
        return holder
    }

    open fun getLayout(viewType: Int): Int {
        return mResLayout
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        bindViewHolder(holder, position, null)
    }


    // 这里的 payloads就是UserItemDiffCallBack中getChangePayload中返回的Object集合
    // 如果某个条目没有调用UserItemDiffCallBack#getChangePayload方法，那么那个条目对应的
    // onBindViewHolder中payloads就会为空数组对象

    //由于我返回的判断新旧数据的url是否相同，所以直接更新一个item的照片就可以了，对于Item其他的TextView对应的
    //name和age，数据没有变化，就没有必要更新了。
    //这里就是上面所说的，可以精确到某个View的更新了，比notifyItemChanged更加有效了。
    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        bindViewHolder(holder, position, payloads)
    }

    open fun bindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder.itemView.setOnClickListener {
            mItemClickListener?.onClick(position, mList[position])
            clickPosition = position
            notifyDataSetChanged()
        }
        holder.bindViewHolder(mList, position, payloads, position == clickPosition)
    }

    fun setData(list: MutableList<T>) {
        mClazzDiff?.also {
            val con = it.getConstructor(List::class.java, List::class.java)
            val p: BaseItemDiffCallBack<T> = con.newInstance(mList, list) as BaseItemDiffCallBack<T>

            // 获取DiffResut结果
            val diffResult = DiffUtil.calculateDiff(p)
            //按照DiffeResult定义好的逻辑，更新数据
            mList.clear()
            mList.addAll(list)
            //使用DiffResult分发给adapter热更新
            diffResult.dispatchUpdatesTo(this)
        } ?: run {
            //按照DiffeResult定义好的逻辑，更新数据
            mList.clear()
            mList.addAll(list)
            notifyDataSetChanged()
        }
    }

    interface ItemClickListener {
        fun <T> onClick(position: Int, bean: T)
    }

    interface ViewClickListener {
        fun <T> onClick(viewId: Int, position: Int, bean: T)
    }
}