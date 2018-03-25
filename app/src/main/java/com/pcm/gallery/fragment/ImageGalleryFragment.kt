package com.pcm.gallery.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.pcm.gallery.R
import com.pcm.gallery.adapter.AdapterMediaAlbum
import com.pcm.gallery.base.BaseFragment
import com.pcm.gallery.constant.Constant
import com.pcm.gallery.model.ModelAlbum
import com.pcm.gallery.utils.PermissionHelper
import com.pcm.gallery.utils.Rx2Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_image.*

class ImageGalleryFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_image
    }

    private lateinit var mAdapter: AdapterMediaAlbum
    private var mDisposable = CompositeDisposable()

    companion object {
        fun newInstance(): ImageGalleryFragment {
            return ImageGalleryFragment()
        }

        val TAG: String = ImageGalleryFragment::class.java.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = AdapterMediaAlbum(context!!)
        rvImageList.layoutManager = GridLayoutManager(context, 2)
        rvImageList.adapter = mAdapter

        requestPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
    }

    private fun requestPermission() {
        if (PermissionHelper.hasPermissions(context!!, Constant.STORAGE_PERMISSION)) {
            onPermissionGranted()
        } else {
            PermissionHelper.requestPermissions(this, Constant.STORAGE_PERMISSION, Constant.REQUEST_CODE_STORAGE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionHelper.hasPermissions(context!!, Constant.STORAGE_PERMISSION)) {
            onPermissionGranted()
        } else {
            if (PermissionHelper.shouldShowPermissionRationale(this, Constant.STORAGE_PERMISSION)) {
                // permission denied only
                showPermissionRationale()
            } else {
                // permission denied with don't ask
                onPermissionDenied()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_PERMISSION_SETTING) {
            if (PermissionHelper.hasPermissions(context!!, Constant.STORAGE_PERMISSION)) {
                onPermissionGranted()
            }
        }
    }

    private fun onPermissionDenied() {
        PermissionHelper.showDialog(context!!, getString(R.string.permission_setting),
                getString(R.string.permission_setting_screen),
                getString(R.string.setting), object : PermissionHelper.OnDialogCloseListener {
            override fun onDialogClose(dialog: DialogInterface, buttonType: Int) {
                PermissionHelper.openSettingScreen(this@ImageGalleryFragment,
                        Constant.REQUEST_CODE_PERMISSION_SETTING)
            }
        })
    }

    private fun showPermissionRationale() {
        PermissionHelper.showDialog(context!!, getString(R.string.permission),
                getString(R.string.permission_message),
                getString(R.string.allow), object : PermissionHelper.OnDialogCloseListener {
            override fun onDialogClose(dialog: DialogInterface, buttonType: Int) {
                PermissionHelper.requestPermissions(this@ImageGalleryFragment,
                        Constant.STORAGE_PERMISSION,
                        Constant.REQUEST_CODE_STORAGE_PERMISSION)
            }
        })
    }

    private fun onPermissionGranted() {
        setAlbumData()
    }

    private fun setAlbumData() {
        mDisposable.add(Rx2Helper.getImageAlbum(context!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<ArrayList<ModelAlbum>> {
                    override fun accept(arrayList: ArrayList<ModelAlbum>?) {
                        mAdapter.setData(arrayList!!)
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        if (t != null) {
                            t.printStackTrace()
                        } else {
                            Log.e(TAG, "Something went wrong")
                        }
                    }
                }))
    }
}
