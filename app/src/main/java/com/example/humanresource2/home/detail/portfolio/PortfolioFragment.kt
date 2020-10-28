package com.example.humanresource2.home.detail.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentPortfolioBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient

class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var recyclerPortfolio : RecyclerView
    private lateinit var sharePref: PreferencesHelper
    private lateinit var viewModel: PortfolioViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentPortfolioBinding.inflate(inflater)
        sharePref = PreferencesHelper(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(PortfolioApiService::class.java)
        viewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)
        viewModel.setSharePreference(sharePref)

        if (service != null) {
            viewModel.setServicePortfolio(service)
        }

        recyclerPortfolio = binding.recycleDetailsPortfolio
        recyclerPortfolio.adapter = PortfolioAdapter(arrayListOf())
        recyclerPortfolio.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiPortfolio()
        subscribeLiveData()
        return binding.root


        return binding.root
    }


    private fun subscribeLiveData() {
        viewModel.isResponsePortfolio.observe(viewLifecycleOwner, Observer {
            (binding.recycleDetailsPortfolio.adapter as PortfolioAdapter).addList(it)
        })
    }
}