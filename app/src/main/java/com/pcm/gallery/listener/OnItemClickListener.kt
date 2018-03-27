package com.pcm.gallery.listener

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(v: View, obj: T, position: Int)
}