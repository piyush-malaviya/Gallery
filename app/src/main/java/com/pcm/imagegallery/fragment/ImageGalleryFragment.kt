package com.pcm.imagegallery.fragment

import android.os.Bundle
import android.view.View
import com.pcm.imagegallery.R
import com.pcm.imagegallery.base.BaseFragment

class ImageGalleryFragment : BaseFragment() {

    override fun getLayoutResource(): Int {
        return R.layout.fragment_image
    }

    companion object {
        fun newInstance(): ImageGalleryFragment {
            return ImageGalleryFragment()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
