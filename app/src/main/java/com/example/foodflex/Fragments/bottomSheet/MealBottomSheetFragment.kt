package com.example.foodflex.Fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodflex.Activities.MealActivity
import com.example.foodflex.Fragments.HomeFragment
import com.example.foodflex.MainActivity
import com.example.foodflex.R
import com.example.foodflex.databinding.FragmentMealBottomSheetBinding
import com.example.foodflex.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "Parasm"

class MealBottomSheetFragment : BottomSheetDialogFragment(){

    private var mealId : String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            mealId = it!!.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNealById(mealId!!)

        observeMeal()
        dialogCliked()
    }

    private fun dialogCliked() {
        binding.bottomSheet.setOnClickListener {
            if (mealName!=null && mealThumb!=null){
                val intent = Intent(activity , MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID , mealId)
                    putExtra(HomeFragment.MEAL_NAME , mealName)
                    putExtra(HomeFragment.MEAL_THUMB , mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }
    private var mealName:String? = null
    private var mealThumb:String? = null

    private fun observeMeal() {
        viewModel.observerBottomSheetMealLivedata().observe(viewLifecycleOwner , Observer {meals->
            Glide.with(this).load(meals.strMealThumb).into(binding.imageView2)
            binding.cate.text = meals.strCategory
            binding.loc.text = meals.strArea
            binding.mealName.text = meals.strMeal

            mealName = meals.strMeal
            mealThumb = meals.strMealThumb
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String): MealBottomSheetFragment {
            return MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
        }
    }



}