package com.example.foodflex

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodflex.LifeCycleObserver.lifeCycleObserver
import com.example.foodflex.RoomDatabase.MealsDatabase
import com.example.foodflex.databinding.ActivityMainBinding
import com.example.foodflex.viewModel.HomeViewModel
import com.example.foodflex.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel : HomeViewModel by lazy {
        val mealsDatabase = MealsDatabase.getDatabase(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealsDatabase)
        ViewModelProvider(this , homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(lifeCycleObserver())

        NavigationUI.setupWithNavController(binding.btnNav ,
            Navigation.findNavController(this , R.id.fragment_container)
        )

    }
}

