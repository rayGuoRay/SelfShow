package com.self.show.selfshow

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem

/**
 * Created by guolei on 18-1-17.
 */
class HomeActivity: AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {

    private var mTitleTextView: TextView? = null
    private var mContentViewPager: ViewPager? = null//by bindView(R.id.home_vp_content)
    private var mContentBNBar: BottomNavigationBar? = null //by bindView(R.id.home_bn_bar)

    private var mTabTextArray: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mTitleTextView = findViewById(R.id.component_title);
        mContentViewPager = findViewById(R.id.home_vp_content)
        mContentBNBar = findViewById(R.id.home_bn_bar)
        initView()
    }

    private fun initView() {
        initBottomView()
    }

    private fun initBottomView() {
        mTabTextArray = resources.getStringArray(R.array.home_bottom_navation_tab)
        val bottomActiveIconArray = resources.obtainTypedArray(R.array.home_bottom_navigation_active)
        val bottomNormalIconArray = resources.obtainTypedArray(R.array.home_bottom_navigation_normal)
        for (i in mTabTextArray!!.indices) {
            val activeResId = bottomActiveIconArray.getResourceId(i, 0)
            val normalResId = bottomNormalIconArray.getResourceId(i, 0)
            val navigationItem = BottomNavigationItem(activeResId, mTabTextArray!![i])
            navigationItem.setInactiveIconResource(normalResId)
//            navigationItem.setActiveColor(R.color.colorDefaultActive)
//            navigationItem.setInActiveColor(R.color.colorDefaultInActive)
            mContentBNBar!!.addItem(navigationItem)
        }
        bottomActiveIconArray.recycle()
        bottomNormalIconArray.recycle()
//        mContentBNBar!!.setMode(BottomNavigationBar.MODE_SHIFTING)
        mContentBNBar!!.initialise()
        mContentBNBar!!.setTabSelectedListener(this)
    }

    override fun onTabSelected(position: Int) {
        mTitleTextView!!.text = mTabTextArray!![position]
    }

    override fun onTabUnselected(position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTabReselected(position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

