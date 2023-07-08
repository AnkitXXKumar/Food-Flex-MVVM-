package com.example.foodflex.Retrofit

import com.example.foodflex.Pojo.CategoryList
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.Pojo.MealList
import com.example.foodflex.Pojo.RealCategoryMeals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("categories.php")
    fun getCategoryMeals():Call<RealCategoryMeals>


    @GET("lookup.php")
    fun getMealDetailes(@Query("i")id:String):Call<MealList>


    @GET("filter.php")
    fun getPopularItem(@Query("c")categoryName : String) : Call<CategoryList>

    @GET("filter.php")
    fun getCategoryMealDetailes(@Query("c")categoryName : String) : Call<CategoryList>


    @GET("search.php")
    fun getSearchMeals(@Query("s")mealName:String):Call<MealList>


}