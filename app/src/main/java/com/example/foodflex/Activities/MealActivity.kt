package com.example.foodflex.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.foodflex.Fragments.HomeFragment
import com.example.foodflex.Pojo.Meal
import com.example.foodflex.R
import com.example.foodflex.RoomDatabase.MealsDatabase
import com.example.foodflex.databinding.ActivityMealBinding
import com.example.foodflex.viewModel.MealViewModel
import com.example.foodflex.viewModel.MealViewModelFacotry

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var MEAL_ID : String
    private lateinit var MEAL_Name : String
    private lateinit var MEAL_Thumb : String
    private lateinit var mealViewModel : MealViewModel
    private lateinit var ytLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealsDatabase = MealsDatabase.getDatabase(this)
        val viewModeFactory = MealViewModelFacotry(mealsDatabase)
        mealViewModel = ViewModelProvider(this , viewModeFactory)[MealViewModel::class.java]
        getMealInformationFromIntent()
        setInfoInViews()

        mealViewModel.getMealDetailes(MEAL_ID)
        oberverMealDetailLiveData()
        openYoutube()
        onFavouriteClicked()


    }

    private fun onFavouriteClicked() {
        binding.fabSave.setOnClickListener {
            mealToSave?.let { 
                mealViewModel.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openYoutube() {
        binding.ytBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse(ytLink))
            startActivity(intent)
        }
    }
    private var mealToSave : Meal? = null
    private fun oberverMealDetailLiveData() {
        mealViewModel.observerMealDetailLivedata().observe(this , object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                val meal = t
                mealToSave = meal
                binding.mealCate.text = meal!!.strCategory
                binding.mealLoc.text = meal.strArea
                binding.instructions.text=meal.strInstructions
                ytLink = meal.strYoutube.toString()
                binding.progressBar.visibility = View.GONE
            }

        })
    }

    private fun setInfoInViews() {
        Glide.with(this).load(MEAL_Thumb).into(binding.imgMealDetail)
        binding.apply{
            collapsingToolbar.title = MEAL_Name
            collapsingToolbar.setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    applicationContext,
                R.color.white)
            )

            collapsingToolbar.setExpandedTitleColor(
                ContextCompat.getColor(
                    applicationContext,
                R.color.white)
            )
        }
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        MEAL_ID = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        MEAL_Name = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        MEAL_Thumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}