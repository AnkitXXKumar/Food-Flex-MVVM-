package com.example.foodflex.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodflex.Pojo.CategoryList
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){

    private var categoryDetailes = MutableLiveData<List<CategoryMeals>>()

    fun getCategoryMealsDetailes(name : String){

        RetrofitInstance.api.getCategoryMealDetailes(name).enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    categoryDetailes.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("VC" , t.toString())
            }

        })
    }

    fun onbserveCategoryDetailes() :LiveData<List<CategoryMeals>>{
        return categoryDetailes
    }
}