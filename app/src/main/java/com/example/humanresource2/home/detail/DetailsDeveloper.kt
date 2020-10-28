package com.example.humanresource2.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityDetailsDeveloperBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.HomeFragment
import com.example.humanresource2.profile.ViewPagerAdapter
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.DetailsApiService

class DetailsDeveloper : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsDeveloperBinding
    private lateinit var sharePref : PreferencesHelper
    private lateinit var viewModel: DetailsViewModel
    private lateinit var viewPager : ViewPagerAdapter

    companion object {
        const val ID_DEV = "ID_DEV"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_developer)
        val intent = intent.getStringExtra(HomeFragment.ID_DEV)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(DetailsApiService::class.java)
        if (service != null) {
            viewModel.setDetailsService(service)
        }
        subscribeLiveData()
        val id = intent?.toInt()
        if (id != null) {
            viewModel.detailsCallApi(id)
        }
        viewPager = ViewPagerAdapter(supportFragmentManager)
        binding.viewProfile.adapter = viewPager
        binding.tabDetails.setupWithViewPager(binding.viewProfile)
    }

    private fun subscribeLiveData() {
        viewModel.isProgressLiveData.observe(this, Observer {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        })
        viewModel.detailsLiveData.observe(this, Observer {
            Glide.with(this@DetailsDeveloper)
                .load("http://54.160.226.42:5000/uploads/${it.data?.photo.toString()}")
                .into(binding.circleImageView)
            binding.detailsName.text = it.data?.nameDeveloper.toString()
            binding.detailsJob.text = it.data?.job.toString()
            binding.detailsLocation.text = it.data?.location.toString()
            binding.detailsDescription.text = it.data?.description.toString()
            binding.detailsSkill.text = it.data?.skill.toString()
            binding.detailsEmail.text = it.data?.email.toString()
            binding.detailsInstagram.text = it.data?.instagram.toString()
            binding.detailsGithub.text = it.data?.github.toString()
            binding.detailsGitlab.text = it.data?.gitlab.toString()
        })
    }
}