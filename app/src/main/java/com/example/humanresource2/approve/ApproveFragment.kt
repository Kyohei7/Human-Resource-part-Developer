package com.example.humanresource2.approve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentApproveBinding

class ApproveFragment : Fragment() {

    private lateinit var binding: FragmentApproveBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approve, container, false)
    }
}