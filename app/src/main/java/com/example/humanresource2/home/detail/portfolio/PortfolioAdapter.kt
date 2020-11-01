package com.example.humanresource2.home.detail.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.humanresource2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_portfolio.view.*

class PortfolioAdapter(val portfolio: ArrayList<PortfolioModel>): RecyclerView.Adapter<PortfolioAdapter.HolderPortfolio>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioAdapter.HolderPortfolio {
        return HolderPortfolio(LayoutInflater.from(parent.context).inflate(R.layout.item_portfolio, parent, false))
    }

    override fun getItemCount(): Int = portfolio.size

    override fun onBindViewHolder(holder: PortfolioAdapter.HolderPortfolio, position: Int) {
        val portfolio = portfolio[position]
        Picasso.get().load("http://18.234.106.45:8080/uploads/" + portfolio.photo).into(holder.view.iv_img_portfolio)
    }

    class HolderPortfolio(val view: View): RecyclerView.ViewHolder(view)

    fun addList(newList: List<PortfolioModel>) {
        portfolio.clear()
        portfolio.addAll(newList)
        notifyDataSetChanged()
    }


}