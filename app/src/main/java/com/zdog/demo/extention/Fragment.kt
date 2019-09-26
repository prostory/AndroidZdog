package com.zdog.demo.extention

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zdog.demo.R
import com.zdog.demo.core.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun FragmentActivity.addFragment(fragment: BaseFragment, func: FragmentTransaction.() -> Unit = {}) =
        supportFragmentManager.inTransaction {
            func(this)
            replace(R.id.fragmentContainer, fragment, fragment::class.java.simpleName)
        }

inline fun BaseFragment.addFragment(fragment: BaseFragment, func: FragmentTransaction.() -> Unit = {}) =
        activity?.addFragment(fragment, func)