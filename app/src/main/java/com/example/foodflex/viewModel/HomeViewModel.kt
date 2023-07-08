package com.example.foodflex.viewModel

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.foodflex.Pojo.*
import com.example.foodflex.Retrofit.RetrofitInstance
import com.example.foodflex.RoomDatabase.MealsDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase : MealsDatabase
) : ViewModel(){

    private var RandomMEalLiveData = MutableLiveData<Meal>()
    private var PopularItemLiveData = MutableLiveData<List<CategoryMeals>>()
    private var CategoryItemLiveData = MutableLiveData<List<Category>>()
    private var favouriteMealLiveData = mealDatabase.mealsDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var searchLiveData = MutableLiveData<List<Meal>>()

    init {
        getRandomMeals()
    }

    fun getRandomMeals(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    RandomMEalLiveData.value = response.body()!!.meals[0]

                }
                else
                {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("ERR" , t.toString())
            }

        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    PopularItemLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("FX" , t.toString())
            }

        })
    }
    fun getCategories(){
        RetrofitInstance.api.getCategoryMeals().enqueue(object : Callback<RealCategoryMeals>{
            override fun onResponse(
                call: Call<RealCategoryMeals>,
                response: Response<RealCategoryMeals>
            ) {
                if(response.body() != null){
                    CategoryItemLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<RealCategoryMeals>, t: Throwable) {
                Log.d("CX" , t.toString())
            }

        })
    }

    fun getNealById(id : String){
        RetrofitInstance.api.getMealDetailes(id).enqueue(object  : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("CXX" , t.toString())
            }

        })
    }

    fun searchMeals(mealName : String){
        RetrofitInstance.api.getSearchMeals(mealName).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meale = response.body()?.meals
                meale?.let {
                    searchLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }



    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealsDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealsDao().insert(meal)
        }
    }

    fun observerSearchMealLivedata():LiveData<List<Meal>>{
        return searchLiveData
    }


    fun observerRandomMealLivedata():LiveData<Meal>{
        return RandomMEalLiveData
    }

    fun observerBottomSheetMealLivedata():LiveData<Meal>{
        return bottomSheetMealLiveData
    }



    fun observercategoryMealLivedata():LiveData<List<Category>>{
        return CategoryItemLiveData
    }

    fun observerCategoryMealLivedata():LiveData<List<CategoryMeals>>{
        return PopularItemLiveData
    }

    fun observerFavoutiteMealLiveData():LiveData<List<Meal>>{
        return favouriteMealLiveData
    }

}