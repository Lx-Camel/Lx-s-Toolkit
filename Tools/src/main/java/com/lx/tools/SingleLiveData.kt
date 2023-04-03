package com.lx.tools

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author liux
 * @date 2022/10/11
 * @desc liveData工具类，解决数据倒灌
 */
class SingleLiveData<T> : MediatorLiveData<T>() {

    private val mPending = AtomicBoolean(false)


    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            logW("Multiple observers registered but only one will be notified of changes.")
        }
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }


    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }


    @MainThread
    fun call() {
        value = null
    }
}