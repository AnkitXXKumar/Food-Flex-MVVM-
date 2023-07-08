package com.example.foodflex.Activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodflex.Adapters.CategoryDetailAdapter
import com.example.foodflex.Fragments.HomeFragment
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.R
import com.example.foodflex.databinding.ActivityCategoryMealsBinding
import com.example.foodflex.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var DetailAdapet : CategoryDetailAdapter
    private lateinit var viewModel : CategoryMealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepairRecycler()

        viewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        val name = intent.getStringExtra(HomeFragment.MEAL_CATEGORY)
        Log.d("CC" , "$name")

        viewModel.getCategoryMealsDetailes(name = name!!)

        viewModel.onbserveCategoryDetailes().observe(this , Observer { meals->
            binding.textView4.text = "Total Items : ${meals.size}"
            DetailAdapet.setDetailes(meals as ArrayList<CategoryMeals>)

        })


    }

    private fun prepairRecycler() {
        DetailAdapet = CategoryDetailAdapter()
        binding.detailRec.apply {
            layoutManager = GridLayoutManager(this@CategoryMealsActivity ,2 ,GridLayoutManager.VERTICAL , false)
            adapter = DetailAdapet
        }
    }
}