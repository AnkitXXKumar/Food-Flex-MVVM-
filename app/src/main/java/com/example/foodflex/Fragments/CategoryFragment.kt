package com.example.foodflex.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodflex.Activities.CategoryMealsActivity
import com.example.foodflex.Adapters.CategoryDetailAdapter
import com.example.foodflex.Adapters.Categoryadapter
import com.example.foodflex.MainActivity
import com.example.foodflex.Pojo.Category
import com.example.foodflex.R
import com.example.foodflex.databinding.FragmentCategoryBinding
import com.example.foodflex.viewModel.HomeViewModel
import java.util.*
import kotlin.collections.ArrayList


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: Categoryadapter
    private lateinit var homeviewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeviewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepairRecyclerView()

        onserverCategories()
        onCateClick()
    }

    private fun onCateClick() {
        categoryAdapter.ItemClick = {category ->
            HomeFragment().startNew(requireActivity(),HomeFragment.MEAL_CATEGORY,category)
        }
    }

    private fun onserverCategories() {
        homeviewModel.observercategoryMealLivedata().observe(viewLifecycleOwner  , androidx.lifecycle.Observer {meals->
            categoryAdapter.setCategory(meals as ArrayList<Category>)
        })
    }

    private fun prepairRecyclerView() {
        categoryAdapter = Categoryadapter()
        binding.categoryRecycler.apply {
            layoutManager = GridLayoutManager(context , 3 ,GridLayoutManager.VERTICAL , false)
            adapter = categoryAdapter
        }
    }
}