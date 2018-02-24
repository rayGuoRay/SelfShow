package com.self.show.selfshow

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.self.show.selfshow.base.BaseActivity
import com.self.show.selfshow.fragment.AllRankFragment
import com.self.show.selfshow.fragment.SelfCollectFragment
import com.self.show.selfshow.fragment.UsRankFragment
import org.w3c.dom.Text

/**
 * Created by guolei on 18-1-17.
 */
class HomeActivity: BaseActivity() {

    private lateinit var mTitleTextView: TextView
    private lateinit var mContentViewPager: ViewPager //by bindView(R.id.home_vp_content)
    private lateinit var mContentBNBar: BottomNavigationBar //by bindView(R.id.home_bn_bar)
    private lateinit var mTabTextArray: Array<String>
    private lateinit var mHomeFragment: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mTitleTextView = findViewById(R.id.component_title) as TextView
        mContentViewPager = findViewById(R.id.home_vp_content) as ViewPager
        mContentBNBar = findViewById(R.id.home_bn_bar) as BottomNavigationBar
        initView()
    }

    private fun initView() {
        initBottomView()
        initTitleView()
        initViewPager()
    }

    private fun initTitleView() {
        mTitleTextView.text = mTabTextArray[0]
    }

    private fun initBottomView() {
        mTabTextArray = resources.getStringArray(R.array.home_bottom_navigation_tab)
        val bottomActiveIconArray = resources.obtainTypedArray(R.array.home_bottom_navigation_active)
        val bottomNormalIconArray = resources.obtainTypedArray(R.array.home_bottom_navigation_normal)
        for (i in mTabTextArray.indices) {
            val activeResId = bottomActiveIconArray.getResourceId(i, 0)
            val normalResId = bottomNormalIconArray.getResourceId(i, 0)
            val navigationItem = BottomNavigationItem(activeResId, mTabTextArray[i])
            navigationItem.setInactiveIconResource(normalResId)
            mContentBNBar.addItem(navigationItem)
        }
        bottomActiveIconArray.recycle()
        bottomNormalIconArray.recycle()
        mContentBNBar.initialise()
        mContentBNBar.setFirstSelectedPosition(0)
        mContentBNBar.setTabSelectedListener(object: BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {}

            override fun onTabUnselected(position: Int) {}

            override fun onTabSelected(position: Int) {
                mContentViewPager.currentItem = position
            }
        })
    }

    private fun initViewPager() {
        mHomeFragment = listOf<Fragment>(UsRankFragment(), AllRankFragment(), SelfCollectFragment())
        mContentViewPager.adapter = HomeFragmentAdapter(supportFragmentManager)
        mContentViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                mContentBNBar.selectTab(position)
            }
        })
        mContentViewPager.currentItem = 0
    }

    inner class HomeFragmentAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return mHomeFragment[position]
        }

        override fun getCount(): Int {
            return mHomeFragment.size
        }
    }
}

