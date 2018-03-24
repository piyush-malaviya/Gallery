package com.pcm.imagegallery.fragment

import com.pcm.imagegallery.R
import com.pcm.imagegallery.base.BaseFragment

class VideoGalleryFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video
    }

    companion object {
        fun newInstance(): VideoGalleryFragment {
            return VideoGalleryFragment()
        }
    }
}