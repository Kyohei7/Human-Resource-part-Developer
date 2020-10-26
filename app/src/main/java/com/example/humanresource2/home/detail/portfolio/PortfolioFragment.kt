package com.example.humanresource2.home.detail.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentPortfolioBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }
}