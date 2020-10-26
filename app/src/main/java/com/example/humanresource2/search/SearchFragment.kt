package com.example.humanresource2.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentSearchBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.HomeAdapter
import com.example.humanresource2.home.HomeFragment
import com.example.humanresource2.home.HomeModel
import com.example.humanresource2.home.detail.DetailsDeveloper
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.HomeApiService

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var sharePref: PreferencesHelper
    private lateinit var RecyclerDev : HomeAdapter
    private lateinit var viewModel: SearchViewModel
    private var list : List<HomeModel> = listOf()
    private lateinit var searchView : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        sharePref = PreferencesHelper(activity as AppCompatActivity)
        searchView = binding.searchview

        val service = ApiClient.getApiClient(activity as AppCompatActivity)?.create(HomeApiService::class.java)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        if (service != null) {
            viewModel.setDevService(service)
        }

        viewModel.callDevApi()
        subsribeLiveData()

        setupRecyclerView()
        return binding.root
    }


    private fun setupRecyclerView() {
        RecyclerDev = HomeAdapter(arrayListOf(), object: HomeAdapter.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                val intent = Intent(activity as AppCompatActivity, DetailsDeveloper::class.java)
                intent.putExtra(HomeFragment.ID_DEV, id)
                startActivity(intent)
            }
        })
        binding.recyclerSearch.adapter = RecyclerDev
        binding.recyclerSearch.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    private fun subsribeLiveData() {
        viewModel.isLoadingProgressBarliveData.observe(activity as AppCompatActivity, Observer {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        })
        viewModel.toastLiveData.observe(activity as AppCompatActivity, Observer {
            if (it) {
                Toast.makeText(activity as AppCompatActivity, "Failed Get" , Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.DevLiveData.observe(activity as AppCompatActivity, Observer {
            (binding.recyclerSearch.adapter as HomeAdapter).addList(it)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    var search: ArrayList<HomeModel> = ArrayList()
                    for (data in list) {
                        if (data.name.contains(query) || data.location.contains(query)) {
                            search.add(data)
                        }
                    }
                    (binding.recyclerSearch.adapter as HomeAdapter).addList(search)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    var search: ArrayList<HomeModel> = ArrayList()
                    for (data in list) {
                        if (data.name.contains(newText) || data.location.contains(newText)) {
                            search.add(data)
                        }
                    }
                    (binding.recyclerSearch.adapter as HomeAdapter).addList(search)
                    return false
                }
            })
        })
    }
}