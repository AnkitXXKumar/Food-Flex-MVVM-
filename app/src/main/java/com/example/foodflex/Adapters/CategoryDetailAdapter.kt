package com.example.foodflex.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodflex.Pojo.Category
import com.example.foodflex.Pojo.CategoryList
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.R

class CategoryDetailAdapter : RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolder>() {


     var categoryDetailList = ArrayList<CategoryMeals>()


    fun setDetailes(categoryDetailList : ArrayList<CategoryMeals>){
        this.categoryDetailList = categoryDetailList
        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.detailCategory)
        val imageee = itemView.findViewById<ImageView>(R.id.detailImagee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_detailes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoryDetailList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryDetailList[position].strMealThumb)
            .into(holder.imageee)
        holder.name.text = categoryDetailList[position].strMeal
    }
}