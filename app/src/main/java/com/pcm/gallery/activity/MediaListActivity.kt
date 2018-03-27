package com.pcm.gallery.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.pcm.gallery.R
import com.pcm.gallery.adapter.AdapterMedia
import com.pcm.gallery.base.BaseAppCompatActivity
import com.pcm.gallery.constant.Constant
import com.pcm.gallery.fragment.ImageGalleryFragment
import com.pcm.gallery.listener.OnItemClickListener
import com.pcm.gallery.model.ModelAlbum
import com.pcm.gallery.model.ModelMedia
import com.pcm.gallery.utils.PermissionHelper
import com.pcm.gallery.utils.Rx2Helper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_image_list.*

class MediaListActivity : BaseAppCompatActivity(), OnItemClickListener<ModelMedia> {

    companion object {
        fun getActivityIntent(context: Context, album: ModelAlbum): Intent {
            val intent = Intent(context, MediaListActivity::class.java)
            intent.putExtra(Constant.DATA, album)
            return intent
        }

        val TAG: String = MediaListActivity::class.java.simpleName
    }

    private var mDisposable = CompositeDisposable()
    private lateinit var mAdapter: AdapterMedia
    private var album: ModelAlbum? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null && intent.hasExtra(Constant.DATA)) {
            album = intent.getParcelableExtra(Constant.DATA)
        }

        initView();
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_image_list;
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        if (supportActionBar != null) {
            if (album != null) {
                supportActionBar!!.title = album!!.name
            }
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        mAdapter = AdapterMedia(this)
        rvMediaList.layoutManager = GridLayoutManager(this, 3)
        rvMediaList.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        requestPermission()
    }

    private fun requestPermission() {
        if (PermissionHelper.hasPermissions(this, Constant.STORAGE_PERMISSION)) {
            onPermissionGranted()
        } else {
            PermissionHelper.requestPermissions(this, Constant.STORAGE_PERMISSION, Constant.REQUEST_CODE_STORAGE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionHelper.hasPermissions(this, Constant.STORAGE_PERMISSION)) {
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
            if (PermissionHelper.hasPermissions(this, Constant.STORAGE_PERMISSION)) {
                onPermissionGranted()
            }
        }
    }

    private fun onPermissionDenied() {
        PermissionHelper.showDialog(this, getString(R.string.permission_setting),
                getString(R.string.permission_setting_screen),
                getString(R.string.setting), object : PermissionHelper.OnDialogCloseListener {
            override fun onDialogClose(dialog: DialogInterface, buttonType: Int) {
                PermissionHelper.openSettingScreen(this@MediaListActivity,
                        Constant.REQUEST_CODE_PERMISSION_SETTING)
            }
        })
    }

    private fun showPermissionRationale() {
        PermissionHelper.showDialog(this, getString(R.string.permission),
                getString(R.string.permission_message),
                getString(R.string.allow), object : PermissionHelper.OnDialogCloseListener {
            override fun onDialogClose(dialog: DialogInterface, buttonType: Int) {
                PermissionHelper.requestPermissions(this@MediaListActivity,
                        Constant.STORAGE_PERMISSION,
                        Constant.REQUEST_CODE_STORAGE_PERMISSION)
            }
        })
    }

    private fun onPermissionGranted() {
        setImageData()
    }

    private fun setImageData() {

        var flowable: Flowable<ArrayList<ModelMedia>> = Rx2Helper.getImageList(this, album!!.id)
        if (album!!.type == Constant.TYPE_VIDEO) {
            flowable = Rx2Helper.getVideoList(this, album!!.id)
        }

        mDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<ArrayList<ModelMedia>> {
                    override fun accept(arrayList: ArrayList<ModelMedia>?) {
                        mAdapter.setData(arrayList!!)
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        if (t != null) {
                            t.printStackTrace()
                        } else {
                            Log.e(ImageGalleryFragment.TAG, "Something went wrong")
                        }
                    }
                }))

    }

    override fun onItemClick(v: View, obj: ModelMedia, position: Int) {

    }
}
