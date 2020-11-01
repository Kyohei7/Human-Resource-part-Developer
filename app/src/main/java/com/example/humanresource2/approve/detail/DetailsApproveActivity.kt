package com.example.humanresource2.approve.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityDetailsApproveBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient
import com.squareup.picasso.Picasso

class DetailsApproveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsApproveBinding
    private lateinit var viewModel: DetailsApproveViewModel
    private lateinit var sharePref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_approve)
        sharePref = PreferencesHelper(applicationContext)

        val service = ApiClient.getApiClient(this)?.create(DetailsApproveApiService::class.java)
        viewModel = ViewModelProvider(this).get(DetailsApproveViewModel::class.java)
        viewModel.setSharedPreference(sharePref)
        if (service != null) {
            viewModel.setServiceStatus(service)
        }

            setUpListener()
            viewModel.callApiDetails()
            subscribeLiveData()


    }

    private fun subscribeLiveData() {
        viewModel.isDetailApproveResponse.observe(this, Observer {
            binding.approveNameproject.text = it.data.nameProject
            binding.approveNamecompany.text = it.data.nameCompany
            binding.approveDescription.text = it.data.description
            Picasso.get().load("http://18.234.106.45:8080/uploads" + it.data.photo)
                .into(binding.circleImageView3)
        })
        viewModel.isApproveResponseReject.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "You Reject The Offers", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModel.isApproveResponseApprove.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "You Approve The Offers", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun setUpListener() {
        binding.btnReject.setOnClickListener {
            viewModel.statusReject()
        }
        binding.btnApprove.setOnClickListener {
            viewModel.statusAccept()
        }
    }

}