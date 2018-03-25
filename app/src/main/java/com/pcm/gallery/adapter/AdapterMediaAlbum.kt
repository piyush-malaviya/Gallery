package com.pcm.gallery.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pcm.gallery.R
import com.pcm.gallery.model.ModelAlbum
import java.io.File

class AdapterMediaAlbum(context: Context) : RecyclerView.Adapter<AdapterMediaAlbum.ViewHolder>() {

    var mContext: Context = context
    var mAlbumList = ArrayList<ModelAlbum>()

    public fun setData(albumList: ArrayList<ModelAlbum>) {
        mAlbumList = ArrayList(albumList)
        notifyDataSetChanged()
    }

    public fun addAll(albumList: ArrayList<ModelAlbum>) {
        mAlbumList.addAll(ArrayList(albumList))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = mAlbumList.get(position)

        holder.tvAlbumName.text = album.name
        holder.tvCount.text = album.count.toString()

        if (!TextUtils.isEmpty(album.path)) {
            val requestOption = RequestOptions()
                    .error(R.drawable.ic_place_holder)
                    .placeholder(R.drawable.ic_place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(holder.imageView)
                    .load(Uri.fromFile(File(album.path)))
                    .thumbnail(0.4f)
                    .apply(requestOption)
                    .into(holder.imageView)

        }
    }

    override fun getItemCount(): Int {
        return mAlbumList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var tvAlbumName = itemView.findViewById<TextView>(R.id.tvAlbumName)
        var tvCount = itemView.findViewById<TextView>(R.id.tvCount)
    }
}