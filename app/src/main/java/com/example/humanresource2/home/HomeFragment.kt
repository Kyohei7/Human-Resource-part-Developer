package com.example.humanresource2.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentHomeBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.HomeApiService

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharePref : PreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharePref = PreferencesHelper(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val service = ApiClient.getApiClient(this.requireContext())?.create(HomeApiService::class.java)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        if (service != null) {
            viewModel.setHomeService(service)
        }

        binding.recyclerHome.adapter = HomeAdapter()
        binding.recyclerHome.layoutManager = GridLayoutManager(requireActivity(), 2)
        viewModel.callApiHome()
        subscribeLiveData()

        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerHome.adapter as HomeAdapter).addList(it)
        })
    }
}