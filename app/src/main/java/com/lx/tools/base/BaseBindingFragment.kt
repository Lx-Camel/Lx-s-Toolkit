package com.lx.tools.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @author liux
 * @date 2022/12/30
 * @desc
 */
abstract class BaseBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<T>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = method.invoke(null, layoutInflater, container, false) as T
        this.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _binding = null
            }
        })
        return binding.root
    }
}