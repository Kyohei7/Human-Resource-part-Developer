package com.example.humanresource2.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.humanresource2.home.detail.experience.ExperienceFragment
import com.example.humanresource2.home.detail.portfolio.PortfolioFragment

class ViewPagerAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(PortfolioFragment(), ExperienceFragment())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int = fragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Portfolio"
            1 -> "Experience"
            else -> ""
        }
    }
}