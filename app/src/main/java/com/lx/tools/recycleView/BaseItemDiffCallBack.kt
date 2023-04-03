package com.lx.tools.recycleView

import androidx.recyclerview.widget.DiffUtil

/**
 * @author liux
 * @date 2022/6/30
 * @desc
 */
class BaseItemDiffCallBack<T>(oldList: List<T>, newList: List<T>) : DiffUtil.Callback() {

    //旧的数据集合
    var mOldList: List<T> = oldList

    //新的数据集合
    var mNewList: List<T> = newList

    //获取旧的数据量大小
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    //获取新的数据量大小
    override fun getNewListSize(): Int {
        return mNewList.size
    }

    //判断两个条目是否是一致的
    //在真实的项目中，我们一般使用id或者index搜索来判断两条item是否一致
    //如果我们的id一样，在系统里面我就认为两个数据记录是一样的
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return mOldList.get(oldItemPosition).id == mNewList.get(newItemPosition).id
        return false
    }

    //这个需要areItemsTheSame 返回true时才调用
    //即使我们的id是一致的，我们在系统中是同一个对象，但是的name可能更新，或者图像可能更新了
    //这里可以填写自己的逻辑，如果图像是一致的，我就认为内容没有变化
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return TextUtils.equals(
//            mOldList.get(oldItemPosition).url,
//            mNewList.get(newItemPosition).url
//        );
        return false
    }

    //这个调用比较奇葩，要求也蛮多的，它需要areItemsTheSame()返回true，说明是同一条数据
    //但是又需要areContentsTheSame()返回false，告诉你虽然我们是同一条数据，但是我们也有不同的
    //它返回的是Object对象，我这里是返回的是Boolean对象，等会告诉你怎么用这个对象
    //当然了，你也可以返回任意的对象，到时候装换一下就可以了。
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val newEntity = mNewList.get(newItemPosition)
//        val oldEntity = mOldList.get(oldItemPosition)
//        return oldEntity.url.equals(newEntity.url)
        return null
    }
}