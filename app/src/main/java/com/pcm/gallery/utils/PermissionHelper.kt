package com.pcm.gallery.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

public class PermissionHelper {
    companion object {
        fun hasPermission(context: Context, permission: String): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                    || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }

        fun hasPermissions(context: Context, permissions: Array<String>?): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }

            if (permissions == null) {
                return false
            }

            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

            return true;
        }


        fun requestPermissions(activity: Activity, permissions: Array<String>?, requestCode: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            if (permissions == null) {
                return
            }

            val permissionArray = getNonGrantedPermissions(activity, permissions)
            if (permissionArray != null) {
                ActivityCompat.requestPermissions(activity, permissionArray, requestCode)
            }
        }


        fun requestPermissions(fragment: Fragment, permissions: Array<String>?, requestCode: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            if (permissions == null) {
                return
            }

            val permissionArray = getNonGrantedPermissions(fragment.context!!, permissions)
            if (permissionArray != null) {
                fragment.requestPermissions(permissionArray, requestCode)
            }
        }

        private fun getNonGrantedPermissions(context: Context, permissions: Array<String>): Array<String?>? {
            val permissionList: ArrayList<String> = ArrayList()

            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission)
                }
            }
            if (permissionList.size > 0) {
                val permissionArray = arrayOfNulls<String>(permissionList.size)
                permissionList.toArray(permissionArray)
                return permissionArray
            }
            return null
        }

        fun shouldShowPermissionRationale(activity: Activity, permissions: Array<String>?): Boolean {
            if (permissions == null) {
                return false
            }
            for (permission in permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    return true
                }
            }
            return false
        }

        fun shouldShowPermissionRationale(fragment: Fragment, permissions: Array<String>?): Boolean {
            if (permissions == null) {
                return false
            }
            for (permission in permissions) {
                if (fragment.shouldShowRequestPermissionRationale(permission)) {
                    return true
                }
            }
            return false
        }

        fun showDialog(context: Context, title: String, message: String, positiveButtonText: String, listener: OnDialogCloseListener) {
            val builder = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(positiveButtonText, DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        listener.onDialogClose(dialogInterface, OnDialogCloseListener.TYPE_POSITIVE)
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.dismiss()
                        listener.onDialogClose(dialogInterface, OnDialogCloseListener.TYPE_NEGATIVE)
                    })
            builder.show()
        }

        fun openSettingScreen(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + activity.packageName)), requestCode)
        }

        fun openSettingScreen(fragment: Fragment, requestCode: Int) {
            fragment.startActivityForResult(Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + fragment.activity!!.packageName)), requestCode)
        }
    }

    interface OnDialogCloseListener {

        fun onDialogClose(dialog: DialogInterface, buttonType: Int)

        companion object {
            val TYPE_POSITIVE = 1
            val TYPE_NEGATIVE = -1
        }
    }
}