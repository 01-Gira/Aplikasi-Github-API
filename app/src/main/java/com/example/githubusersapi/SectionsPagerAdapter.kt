package com.example.githubusersapi

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = FollowerFragment.getInstance(login)
            1 -> fragment = FollowingFragment.getInstance(login)
        }
        return fragment as Fragment
    }

}