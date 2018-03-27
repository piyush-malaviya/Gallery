package com.pcm.gallery.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pcm.gallery.R
import com.pcm.gallery.listener.OnItemClickListener
import com.pcm.gallery.model.ModelMedia
import java.io.File

class AdapterMedia(context: Context) : RecyclerView.Adapter<AdapterMedia.ViewHolder>() {

    var mContext: Context = context
    var mMediaList = ArrayList<ModelMedia>()
    private var onItemClickListener: OnItemClickListener<ModelMedia>? = null

    public fun setData(albumList: ArrayList<ModelMedia>) {
        mMediaList = ArrayList(albumList)
        notifyDataSetChanged()
    }

    public fun addAll(albumList: ArrayList<ModelMedia>) {
        mMediaList.addAll(ArrayList(albumList))
        notifyDataSetChanged()
    }

    public fun setOnItemClickListener(listener: OnItemClickListener<ModelMedia>?) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = mMediaList.get(position)

        if (!TextUtils.isEmpty(media.imagePath)) {
            val requestOption = RequestOptions()
                    .error(R.drawable.ic_place_holder)
                    .placeholder(R.drawable.ic_place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(holder.imageView)
                    .load(Uri.fromFile(File(media.imagePath)))
                    .thumbnail(0.4f)
                    .apply(requestOption)
                    .into(holder.imageView)
        }

        holder.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onItemClick(v!!, media, position)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return mMediaList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}