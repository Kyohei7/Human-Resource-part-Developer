package com.example.humanresource2.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.humanresource2.R
import com.example.humanresource2.databinding.FragmentProfileBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.login.LoginActivity
import com.example.humanresource2.profile.edit_profile.EditProfileActivity
import com.example.humanresource2.remote.ApiClient
import com.example.humanresource2.service.ProfileApiService
import kotlinx.coroutines.CoroutineScope

class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharePref: PreferencesHelper
    private lateinit var viewPager : ViewPagerAdapter


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

        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity as AppCompatActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        viewPager = ViewPagerAdapter((activity as AppCompatActivity).supportFragmentManager)
        binding.viewProfile.adapter = viewPager
        binding.tabDetailsProfile.setupWithViewPager(binding.viewProfile)

        subscribeLiveData()
        setUpLogout()
        return binding.root
    }

    private fun setUpLogout() {
        binding.logout.setOnClickListener {
            dialog()
        }
    }

    private fun dialog() {
        val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Are You Sure?")
                .setPositiveButton("Logout"){ dialog: DialogInterface?, which: Int ->
                    sharePref.logout()
                    val intent=Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel")  {dialogInterface, i -> dialogInterface.dismiss()
                }
        dialog.show()
    }

    private fun subscribeLiveData() {
        viewModel.profileLiveData.observe(activity as AppCompatActivity, Observer {
            if (it.data.idDeveloper != null) {
                if (it.data.idUser == sharePref.getString(Constant.PREFERENCES_ID)) {
                    sharePref.putString(Constant.PREFERENCES_IS_ID_DEV, it.data.idDeveloper.toString())
                    Glide.with(this@ProfileFragment)
                            .load("http://18.234.106.45:8080/uploads/${it.data.photo.toString()}")
                            .into(binding.circleImageView)
                    binding.profileName.text = it.data.nameDeveloper.toString()
                    binding.job.text = it.data.job
                    binding.location.text = it.data.location
                    binding.description.text = it.data.description
                    binding.skill.text = it.data.skill
                    binding.email.text = it.data.email
                    binding.instagram.text = it.data.instagram
                    binding.github.text = it.data.github
                    binding.gitlab.text = it.data.gitlab
                }
            } else {
                val intent = Intent(activity as AppCompatActivity, EditProfileActivity::class.java)
                startActivity(intent)
            }
        })
    }
}