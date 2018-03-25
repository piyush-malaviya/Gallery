package com.pcm.gallery.utils

import android.database.Cursor
import android.text.TextUtils

class CursorUtils {
    companion object {

        fun getString(cursor: Cursor, column: String): String {
            if (cursor.getColumnIndex(column) == -1) {
                return ""
            }
            val value = cursor.getString(cursor.getColumnIndex(column))
            return if (TextUtils.isEmpty(value)) "" else value
        }

        fun getInt(cursor: Cursor, column: String): Int {
            return cursor.getInt(cursor.getColumnIndex(column))
        }

        fun getLong(cursor: Cursor, column: String): Long {
            return cursor.getLong(cursor.getColumnIndex(column))
        }

        fun getFloat(cursor: Cursor, column: String): Float {
            return cursor.getFloat(cursor.getColumnIndex(column))
        }

        fun getDouble(cursor: Cursor, column: String): Double {
            return cursor.getDouble(cursor.getColumnIndex(column))
        }

        fun getByte(cursor: Cursor, column: String): ByteArray {
            val value = cursor.getBlob(cursor.getColumnIndex(column))
            return value ?: ByteArray(0)
        }

        fun getShort(cursor: Cursor, column: String): Short {
            return cursor.getShort(cursor.getColumnIndex(column))
        }

        fun getType(cursor: Cursor, column: String): Int {
            return cursor.getType(cursor.getColumnIndex(column))
        }
    }
}