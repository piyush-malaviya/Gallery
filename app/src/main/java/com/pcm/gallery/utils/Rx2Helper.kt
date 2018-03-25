package com.pcm.gallery.utils

import android.content.Context
import android.provider.MediaStore
import com.pcm.gallery.constant.Constant
import com.pcm.gallery.model.ModelAlbum
import io.reactivex.Flowable
import java.util.concurrent.Callable

class Rx2Helper {

    companion object {
        /**
         * get image album list
         */
        fun getImageAlbum(context: Context): Flowable<ArrayList<ModelAlbum>> {
            return Flowable.fromCallable(object : Callable<ArrayList<ModelAlbum>> {
                override fun call(): ArrayList<ModelAlbum> {
                    val albumList = ArrayList<ModelAlbum>()
                    val projection = arrayOf(MediaStore.Images.Media.BUCKET_ID,
                            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                            "COUNT(*) as count",
                            MediaStore.Images.Media.DATA)
                    val sortBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME

                    val selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME +
                            " != ?) " + "GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME

                    val cursor = context.getContentResolver()
                            .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    projection,
                                    selection,
                                    arrayOf(""),
                                    sortBy)

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            val album = ModelAlbum(
                                    CursorUtils.getString(cursor, MediaStore.Images.Media.BUCKET_ID),
                                    CursorUtils.getString(cursor, MediaStore.Images.Media.BUCKET_DISPLAY_NAME),
                                    CursorUtils.getInt(cursor, "count"),
                                    Constant.TYPE_IMAGE,
                                    CursorUtils.getString(cursor, MediaStore.Images.Media.DATA))
                            albumList.add(album)
                        }
                        cursor.close()
                    }
                    return albumList
                }
            })
        }

        /**
         * get image album list
         */
        fun getVideoAlbum(context: Context): Flowable<ArrayList<ModelAlbum>> {
            return Flowable.fromCallable(object : Callable<ArrayList<ModelAlbum>> {
                override fun call(): ArrayList<ModelAlbum> {
                    val albumList = ArrayList<ModelAlbum>()
                    val projection = arrayOf(MediaStore.Video.Media.BUCKET_ID,
                            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                            "COUNT(*) as count",
                            MediaStore.Video.Media.DATA)
                    val sortBy = MediaStore.Video.Media.BUCKET_DISPLAY_NAME

                    val selection = MediaStore.Video.Media.BUCKET_DISPLAY_NAME +
                            " != ?) " + "GROUP BY (" + MediaStore.Video.Media.BUCKET_DISPLAY_NAME

                    val cursor = context.getContentResolver()
                            .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                    projection,
                                    selection,
                                    arrayOf(""),
                                    sortBy)

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            val album = ModelAlbum(
                                    CursorUtils.getString(cursor, MediaStore.Video.Media.BUCKET_ID),
                                    CursorUtils.getString(cursor, MediaStore.Video.Media.BUCKET_DISPLAY_NAME),
                                    CursorUtils.getInt(cursor, "count"),
                                    Constant.TYPE_VIDEO,
                                    CursorUtils.getString(cursor, MediaStore.Video.Media.DATA))
                            albumList.add(album)
                        }
                        cursor.close()
                    }
                    return albumList
                }
            })
        }
    }
}