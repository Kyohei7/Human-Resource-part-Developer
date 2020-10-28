package com.example.humanresource2.home.detail.experience

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
import com.example.humanresource2.databinding.FragmentExperienceBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient

class ExperienceFragment : Fragment() {

    private lateinit var binding: FragmentExperienceBinding
    private lateinit var recyclerExperience: RecyclerView
    private lateinit var sharePref : PreferencesHelper
    private lateinit var viewModel: ExperienceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentExperienceBinding.inflate(inflater)
        sharePref = PreferencesHelper(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(ExperienceApiService::class.java)
        viewModel = ViewModelProvider(this).get(ExperienceViewModel::class.java)
        viewModel.setSharePreferences(sharePref)

        if (service != null) {
            viewModel.setServiceExperience(service)
        }

        recyclerExperience = binding.recycleDetailsExperience
        recyclerExperience.adapter = ExperienceAdapter(arrayListOf())
        recyclerExperience.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiExperience()
        subscribeLiveData()
        return binding.root


        // Inflate the layout for this fragment
        return binding.root
    }


    private fun subscribeLiveData() {
        viewModel.isResponseExperience.observe(viewLifecycleOwner, Observer {
            (binding.recycleDetailsExperience.adapter as ExperienceAdapter).addList(it)
        })
    }
}