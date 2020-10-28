package com.example.humanresource2.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentHomeBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.detail.DetailsDeveloper
import com.example.humanresource2.profile.ViewPagerAdapter
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.HomeApiService

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharePref : PreferencesHelper
    private lateinit var recycleHome : HomeAdapter
    private lateinit var viewPager : ViewPagerAdapter

    companion object {
        const val ID_DEV = "idDev"
    }

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

        viewModel.callApiHome()
        subscribeLiveData()
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        recycleHome = HomeAdapter(arrayListOf(), object : HomeAdapter.OnClickViewListener {
            override fun OnClick(id: String) {
                Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
                val intent = Intent(activity as AppCompatActivity, DetailsDeveloper::class.java)
                intent.putExtra(ID_DEV, id)
                startActivity(intent)
            }
        })
        binding.recyclerHome.adapter = recycleHome
        binding.recyclerHome.layoutManager = GridLayoutManager(requireActivity(), 2)

    }

    private fun subscribeLiveData() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerHome.adapter as HomeAdapter).addList(it)
        })
    }
}