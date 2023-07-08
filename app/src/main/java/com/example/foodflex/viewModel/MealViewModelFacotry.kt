package com.example.foodflex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodflex.RoomDatabase.MealsDatabase

class MealViewModelFacotry(
    private val mealDatabase : MealsDatabase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }


}