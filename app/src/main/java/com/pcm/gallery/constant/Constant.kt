package com.pcm.gallery.constant

import android.Manifest

class Constant {
    companion object {
        val TYPE_IMAGE = 1
        val TYPE_VIDEO = 2

        val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val REQUEST_CODE_STORAGE_PERMISSION = 123
        val REQUEST_CODE_PERMISSION_SETTING = 456
    }
}