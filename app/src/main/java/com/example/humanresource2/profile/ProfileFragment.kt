package com.example.humanresource2.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentProfileBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.ProfileApiService
import kotlinx.coroutines.CoroutineScope

class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharePref: PreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        sharePref = PreferencesHelper(activity as AppCompatActivity)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val service = ApiClient.getApiClient(activity as AppCompatActivity)?.create(ProfileApiService::class.java)
        if (service != null) {
            viewModel.setProfileService(service)
        }
        val id = sharePref.getString(Constant.PREFERENCES_ID)
        if (id != null) {
            viewModel.getProfileDevApi(id.toInt())
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity as AppCompatActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        subscribeLiveData()
        return binding.root
    }


    private fun subscribeLiveData() {
        viewModel.profileLiveData.observe(activity as AppCompatActivity, Observer {
            if (it.data.idDeveloper != null) {
                if (it.data.idUser == sharePref.getString(Constant.PREFERENCES_ID)) {
                    sharePref.putString(Constant.PREFERENCES_IS_ID_DEV, it.data.idDeveloper.toString())
                    Glide.with(this@ProfileFragment)
                            .load("http://54.160.226.42:5000/uploads/${it.data.photo.toString()}")
                            .into(binding.circleImageView)
                    binding.profileName.text = it.data.nameDeveloper.toString()
                }
            } else {
                val intent = Intent(activity as AppCompatActivity, EditProfileActivity::class.java)
                startActivity(intent)
            }
        })
    }
}