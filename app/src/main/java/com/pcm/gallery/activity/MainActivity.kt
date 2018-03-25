package com.pcm.gallery.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.pcm.gallery.fragment.ImageGalleryFragment
import com.pcm.gallery.model.ModelFragment
import com.pcm.gallery.R
import com.pcm.gallery.base.BaseAppCompatActivity
import com.pcm.gallery.fragment.VideoGalleryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_settings -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        setSupportActionBar(toolbar)

        // tab title and fragment
        val fragmentArray = ArrayList<ModelFragment>()
        fragmentArray.add(ModelFragment(getString(R.string.title_image), ImageGalleryFragment.newInstance()))
        fragmentArray.add(ModelFragment(getString(R.string.title_video), VideoGalleryFragment.newInstance()))

        // set adapter
        viewPager.adapter = MyPagerAdapter(supportFragmentManager, fragmentArray)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }

    class MyPagerAdapter(fm: FragmentManager, fragmentArray: ArrayList<ModelFragment>) : FragmentStatePagerAdapter(fm) {
        var mFragmentArray = fragmentArray

        override fun getItem(position: Int): Fragment {
            return mFragmentArray.get(position).fragment
        }

        override fun getCount(): Int {
            return mFragmentArray.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentArray.get(position).title
        }
    }
}
