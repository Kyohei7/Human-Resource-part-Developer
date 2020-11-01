package com.example.humanresource2.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.example.humanresource2.databinding.ItemDeveloperBinding
import com.example.humanresource2.helper.PreferencesHelper
import com.squareup.picasso.Picasso
import java.util.ArrayList

class HomeAdapter(val items: ArrayList<HomeModel>, val listener: OnClickViewListener): RecyclerView.Adapter<HomeAdapter.HomeHolder>() {


    private lateinit var sharePref: PreferencesHelper

    private fun getPhotoImage(file: String) : String = "http://18.234.106.45:8080/uploads/$file"

    fun addList(list: List<HomeModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): HomeAdapter.HomeHolder {
        return HomeHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_developer, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val item = items[position]
        holder.binding.tvNameDeveloper.text = item.name
        holder.binding.tvJobDeveloper.text = item.job
        holder.binding.tvSkill.text = item.skill
        Picasso.get().load(getPhotoImage(item.photo.toString())).
        into(holder.binding.imgImageDeveloper)
        holder.binding.recyclerHome.setOnClickListener {
            listener.OnClick(item.id)
        }
    }

    class HomeHolder(val binding: ItemDeveloperBinding ): RecyclerView.ViewHolder(binding.root)

    interface OnClickViewListener {
        fun OnClick(id: String)
    }

}