package com.example.humanresource2.home.detail.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import kotlinx.android.synthetic.main.item_experience.view.*

class ExperienceAdapter(val experience: ArrayList<ExperienceModel>): RecyclerView.Adapter<ExperienceAdapter.HolderExperience>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceAdapter.HolderExperience {
        return HolderExperience(LayoutInflater.from(parent.context).inflate(R.layout.item_experience, parent, false))
    }

    override fun getItemCount(): Int = experience.size

    override fun onBindViewHolder(holder: ExperienceAdapter.HolderExperience, position: Int) {
        val experience = experience[position]
        holder.view.name_company.text = experience.companyName
        holder.view.position_experience.text = experience.position
        holder.view.duration.text = experience.duration
        holder.view.description.text = experience.description
    }

    class HolderExperience(val view: View): RecyclerView.ViewHolder(view)

    fun addList(newList: List<ExperienceModel>) {
        experience.clear()
        experience.addAll(newList)
        notifyDataSetChanged()
    }
}