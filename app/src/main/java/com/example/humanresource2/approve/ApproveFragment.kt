package com.example.humanresource2.approve

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.example.humanresource2.approve.detail.DetailsApproveActivity
import com.example.humanresource2.databinding.FragmentApproveBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.ApproveApiService

class ApproveFragment : Fragment() {

    private lateinit var binding: FragmentApproveBinding
    private lateinit var viewModel: ApproveViewModel
    private lateinit var sharePref: PreferencesHelper
    private lateinit var recycleApprove: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentApproveBinding.inflate(inflater)
        sharePref = PreferencesHelper(requireContext())

        val service = ApiClient.getApiClient(this.requireContext())?.create(ApproveApiService::class.java)
        viewModel = ViewModelProvider(this).get(ApproveViewModel::class.java)
        viewModel.setSharedPreferences(sharePref)
        if (service != null) {
            viewModel.setServiceConfirm(service)
        }

        recycleApprove = binding.recyclerApprove
        recycleApprove.adapter = ApproveAdapter(arrayListOf(), object : ApproveAdapter.onAdapterListener {
            override fun onClick(approve: ApproveModel) {
                sharePref.putString(Constant.PREFERENCE_IS_ID_HIRE, approve.id)
                sharePref.putString(Constant.PREFERENCE_IS_STATUS, approve.status)
                Toast.makeText(requireContext(),"TESSSSSSS", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), DetailsApproveActivity::class.java))
            }

        })
        recycleApprove.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.getHireByID()
        subscribeLiveData()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        viewModel.getHireByID()
    }


    private fun  subscribeLiveData() {
        viewModel.isApproveResponse.observe(viewLifecycleOwner, Observer {
            (binding.recyclerApprove.adapter as ApproveAdapter).addList(it)
        })
    }
}