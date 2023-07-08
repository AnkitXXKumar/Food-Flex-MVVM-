package com.example.foodflex.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodflex.Pojo.Meal
import com.example.foodflex.Pojo.MealList
import com.example.foodflex.Retrofit.RetrofitInstance
import com.example.foodflex.RoomDatabase.MealsDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase : MealsDatabase
): ViewModel(){

    private var MealDetailesLiveData = MutableLiveData<Meal>()

    fun getMealDetailes(id : String){

        RetrofitInstance.api.getMealDetailes(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    MealDetailesLiveData.value = response.body()!!.meals[0]

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

    fun observerMealDetailLivedata(): LiveData<Meal> {
        return MealDetailesLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealsDao().insert(meal)
        }
    }



}