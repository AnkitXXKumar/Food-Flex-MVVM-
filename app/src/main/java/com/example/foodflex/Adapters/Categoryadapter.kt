package com.example.foodflex.Adapters

import android.view.LayoutInflater
import android.view.TextureView
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

class Categoryadapter : RecyclerView.Adapter<Categoryadapter.MyViewHolder>() {

    var categoryList: ArrayList<Category> = ArrayList()
    var ItemClick : ((Category)->Unit)? = null

    fun setCategory(categoryList : ArrayList<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.category_name)
        val image = itemView.findViewById<ImageView>(R.id.category_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.image)

        holder.name.text = categoryList[position].strCategory


        holder.itemView.setOnClickListener {
            ItemClick!!.invoke(categoryList[position])
        }
    }
}