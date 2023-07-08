package com.example.foodflex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodflex.RoomDatabase.MealsDatabase

class HomeViewModelFactory(
    private val mealDatabase : MealsDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }


}