package com.example.humanresource2.login.post_profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ActivityPostProfileBinding
import com.example.humanresource2.helper.Constant
import com.example.humanresource2.helper.PreferencesHelper
import com.example.humanresource2.home.HomeActivity
import com.example.humanresource2.remote.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PostProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostProfileBinding
    private lateinit var sharePref: PreferencesHelper
    private lateinit var viewModel: PostProfileViewModel

    private var photo: MultipartBody.Part? = null

    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_profile)
        sharePref = PreferencesHelper(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(PostProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(PostProfileViewModel::class.java)
        viewModel.setSharePref(sharePref)

        if (service != null) {
            viewModel.setPostService(service)
        }

        setUpListener()
        subscribeLiveData()


    }

    private fun subscribeLiveData() {
        viewModel.isPostResponse.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setUpListener() {
        binding.btnSend.setOnClickListener {
            val idUser = sharePref.getString(Constant.PREFERENCES_ID)
            Log.d("IDUSER", "$idUser")
            viewModel.postDeveloper(
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    createPartFromString("Akbar"),
                    photo,
                    createPartFromString("$idUser")
            )
        }
        binding.edit.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else {
                    pickImgGallery()
                }
            } else {
                pickImgGallery()
            }
        }
    }

    private fun pickImgGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    pickImgGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.circleImageView2.setImageURI(data?.data)
            val filePath = getPath(this, data?.data)
            val file = File(filePath)

            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            photo = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("photo", file.name,
                        it1
                )
            }
        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
                uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
                .toRequestBody(mediaType)
    }

}