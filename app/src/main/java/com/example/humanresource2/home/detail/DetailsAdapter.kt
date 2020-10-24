package com.example.humanresource2.home.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class DetailsAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(ExperienceFragment(), PortfolioFragment())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int = fragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Experience"
            1 -> "Portfolio"
            else -> ""
        }
    }


}