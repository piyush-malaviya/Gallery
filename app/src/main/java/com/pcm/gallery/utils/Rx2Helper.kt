package com.pcm.gallery.utils

import android.content.Context
import android.provider.MediaStore
import com.pcm.gallery.constant.Constant
import com.pcm.gallery.model.ModelAlbum
import com.pcm.gallery.model.ModelMedia
import io.reactivex.Flowable
import java.util.concurrent.Callable

class Rx2Helper {

    companion object {

        val SORT_ORDER_ASC = "ASC"
        val SORT_ORDER_DESC = "DESC"

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
         * get video album list
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

        /**
         * get image list
         */
        fun getImageList(context: Context, albumId: String, type: String, order: String): Flowable<ArrayList<ModelMedia>> {
            return Flowable.fromCallable(object : Callable<ArrayList<ModelMedia>> {
                override fun call(): ArrayList<ModelMedia> {
                    val albumList = ArrayList<ModelMedia>()
                    val projection = arrayOf(
                            MediaStore.Images.Media._ID,
                            MediaStore.Images.Media.BUCKET_ID,
                            MediaStore.Images.Media.DISPLAY_NAME,
                            MediaStore.Images.Media.DATE_TAKEN,
                            MediaStore.Images.Media.SIZE,
                            MediaStore.Images.Media.DATA,
                            MediaStore.Images.Media.WIDTH,
                            MediaStore.Images.Media.HEIGHT,
                            MediaStore.Images.Media.ORIENTATION)

                    val sortBy = type + " " + order

                    val selection = MediaStore.Images.Media.BUCKET_ID + " = ? "

                    val cursor = context.getContentResolver()
                            .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    projection,
                                    selection,
                                    arrayOf(albumId),
                                    sortBy)

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            val media = ModelMedia(
                                    CursorUtils.getString(cursor, MediaStore.Images.Media._ID),
                                    albumId,
                                    CursorUtils.getString(cursor, MediaStore.Images.Media.DISPLAY_NAME),
                                    Constant.TYPE_IMAGE,
                                    CursorUtils.getString(cursor, MediaStore.Images.Media.DATA),
                                    CursorUtils.getLong(cursor, MediaStore.Images.Media.DATE_TAKEN),
                                    CursorUtils.getLong(cursor, MediaStore.Images.Media.SIZE),
                                    CursorUtils.getInt(cursor, MediaStore.Images.Media.WIDTH),
                                    CursorUtils.getInt(cursor, MediaStore.Images.Media.HEIGHT),
                                    CursorUtils.getInt(cursor, MediaStore.Images.Media.ORIENTATION)
                            )
                            albumList.add(media)
                        }
                        cursor.close()
                    }
                    return albumList
                }
            })
        }

        fun getImageList(context: Context, albumId: String): Flowable<ArrayList<ModelMedia>> {
            return getImageList(context, albumId, MediaStore.Images.Media.DATE_TAKEN, SORT_ORDER_DESC)
        }

        /**
         * get video list
         */
        fun getVideoList(context: Context, albumId: String, type: String, order: String): Flowable<ArrayList<ModelMedia>> {
            return Flowable.fromCallable(object : Callable<ArrayList<ModelMedia>> {
                override fun call(): ArrayList<ModelMedia> {
                    val albumList = ArrayList<ModelMedia>()
                    val projection = arrayOf(
                            MediaStore.Video.Media._ID,
                            MediaStore.Video.Media.BUCKET_ID,
                            MediaStore.Video.Media.DISPLAY_NAME,
                            MediaStore.Video.Media.DATE_TAKEN,
                            MediaStore.Video.Media.SIZE,
                            MediaStore.Video.Media.DATA,
                            MediaStore.Video.Media.WIDTH,
                            MediaStore.Video.Media.HEIGHT,
                            MediaStore.Video.Media.DURATION)

                    val sortBy = type + " " + order

                    val selection = MediaStore.Video.Media.BUCKET_ID + " = ? "

                    val cursor = context.getContentResolver()
                            .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                    projection,
                                    selection,
                                    arrayOf(albumId),
                                    sortBy)

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            val media = ModelMedia(
                                    CursorUtils.getString(cursor, MediaStore.Video.Media._ID),
                                    albumId,
                                    CursorUtils.getString(cursor, MediaStore.Video.Media.DISPLAY_NAME),
                                    Constant.TYPE_VIDEO,
                                    CursorUtils.getString(cursor, MediaStore.Video.Media.DATA),
                                    CursorUtils.getLong(cursor, MediaStore.Video.Media.DATE_TAKEN),
                                    CursorUtils.getLong(cursor, MediaStore.Video.Media.SIZE),
                                    CursorUtils.getInt(cursor, MediaStore.Video.Media.WIDTH),
                                    CursorUtils.getInt(cursor, MediaStore.Video.Media.HEIGHT),
                                    CursorUtils.getLong(cursor, MediaStore.Video.Media.DURATION)
                            )
                            albumList.add(media)
                        }
                        cursor.close()
                    }
                    return albumList
                }
            })
        }

        fun getVideoList(context: Context, albumId: String): Flowable<ArrayList<ModelMedia>> {
            return getVideoList(context, albumId, MediaStore.Video.Media.DATE_TAKEN, SORT_ORDER_DESC)
        }
    }
}