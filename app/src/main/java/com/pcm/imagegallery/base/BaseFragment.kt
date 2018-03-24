package com.pcm.imagegallery.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pcm.imagegallery.R

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getLayoutResource(), container, false)
    }

    abstract fun getLayoutResource(): Int

    public fun replaceFragment(container: Int, fragment: Fragment, addToBackStack: Boolean, animate: Boolean) {
        if (activity == null) {
            return
        }
        val fm = activity.supportFragmentManager
        val ft = fm.beginTransaction()

        if (animate) {
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        }
        val tag: String? = fragment.javaClass.simpleName
        if (addToBackStack) {
            ft.replace(container, fragment, tag)
            ft.addToBackStack(tag)
            ft.commitAllowingStateLoss()
        } else {
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ft.replace(container, fragment, tag)
            ft.commit()
        }
    }

    /*public fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, animate: Boolean) {
        replaceFragment(R.id.container, fragment, addToBackStack, animate)
    }*/

    public fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}