package com.example.humanresource2.approve

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hire.view.*

class ApproveAdapter(var dataApprove: ArrayList<ApproveModel>, var listener: onAdapterListener): RecyclerView.Adapter<ApproveAdapter.ApproveHolder>() {


    fun addList(list: List<ApproveModel>) {
        dataApprove.clear()
        dataApprove.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ApproveAdapter.ApproveHolder {
        return ApproveHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hire, parent, false
        ))
    }

    override fun getItemCount(): Int = dataApprove.size

    override fun onBindViewHolder(holder: ApproveAdapter.ApproveHolder, position: Int) {
        val approve = dataApprove[position]
        holder.view.name_company.text = approve.nameCompany
        holder.view.name_project.text = approve.nameProject
        holder.view.description.text = approve.description
        holder.view.status_approve.text = approve.status
        Picasso.get().load("http://18.234.106.45:8080/uploads" + approve.photo)
                .into(holder.view.iv_img_approve)
        holder.itemView.setOnClickListener {
            listener.onClick(approve)
        }

    }

    class ApproveHolder(val view: View): RecyclerView.ViewHolder(view)

    interface onAdapterListener {
        fun onClick(approve: ApproveModel)
    }

}