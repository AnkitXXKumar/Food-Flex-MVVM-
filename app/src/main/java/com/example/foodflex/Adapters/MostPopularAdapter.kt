package com.example.foodflex.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.R
import com.example.foodflex.databinding.PopularItemsBinding

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.MyViewHolder>() {


    lateinit var onItemClick:((CategoryMeals) -> Unit)
    var onLongItemClick:((CategoryMeals)->Unit)?=null

    private var mealList = ArrayList<CategoryMeals>()

    fun setMeals(mealList : ArrayList<CategoryMeals>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


    class MyViewHolder(var binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularFoodImages)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClick!!.invoke(mealList[position])
            true
        }
    }
}