package com.example.randommovie.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.randommovie.ui.fragments.FragmentLogIn
import com.example.randommovie.ui.fragments.FragmentRegistration

class ViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragments = ArrayList<Fragment>()
    private val mFragmentsTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return when (position){
            0->{
                FragmentLogIn()
            }
            else->{
                return FragmentRegistration()
            }
        }
    }

    override fun getCount(): Int {
        return mFragments.count()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentsTitleList[position]
    }

    public fun getFragment(position: Int) : Fragment{
        return mFragments[position]
    }

    fun addFragment(fragment : Fragment, title : String){
        mFragments.add(fragment)
        mFragmentsTitleList.add(title)
    }

}