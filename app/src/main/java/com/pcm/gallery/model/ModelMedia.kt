package com.pcm.gallery.model

data class ModelMedia(
        var id: String,
        var albumId: String,
        var title: String,
        var type: Int,
        var imagePath: String,
        var createdDate: Long,
        var size: Long,
        var width: Int,
        var height: Int,
        var orientation: Int,
        var duration: Long,
        var isSelected: Boolean,
        var isSelectionActive: Boolean
) {
    constructor(id: String, albumId: String, title: String,
                type: Int, imagePath: String, createdDate: Long,
                size: Long, width: Int, height: Int,
                orientation: Int)
            : this(id, albumId, title, type, imagePath, createdDate,
            size, width, height, orientation, 0, false, false)

    constructor(id: String, albumId: String, title: String,
                type: Int, imagePath: String, createdDate: Long,
                size: Long, width: Int, height: Int,
                duration: Long)
            : this(id, albumId, title, type, imagePath, createdDate,
            size, width, height, 0, duration, false, false)
}