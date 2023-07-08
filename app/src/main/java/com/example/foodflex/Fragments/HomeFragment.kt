package com.example.foodflex.Fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodflex.Activities.CategoryMealsActivity
import com.example.foodflex.Activities.MealActivity
import com.example.foodflex.Adapters.Categoryadapter
import com.example.foodflex.Adapters.MostPopularAdapter
import com.example.foodflex.Fragments.bottomSheet.MealBottomSheetFragment
import com.example.foodflex.MainActivity
import com.example.foodflex.Pojo.Category
import com.example.foodflex.Pojo.CategoryMeals
import com.example.foodflex.Pojo.Meal
import com.example.foodflex.R
import com.example.foodflex.databinding.FragmentHomeBinding
import com.example.foodflex.viewModel.HomeViewModel
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var HomeMVVM : HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var PopularItemAdapter : MostPopularAdapter
    private lateinit var Categoryadapter : Categoryadapter
    private lateinit var dialog : Dialog

    companion object{
        const val MEAL_ID = "com.example.foodflex.idMEal"
        const val MEAL_NAME = "com.example.foodflex.nameMeal"
        const val MEAL_THUMB = "com.example.foodflex.thumbMeal"
        const val MEAL_CATEGORY = "com.example.foodflex.category"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = context?.let { Dialog(it) }!!
        HomeMVVM = (activity as MainActivity).viewModel
        PopularItemAdapter = MostPopularAdapter()
        Categoryadapter = Categoryadapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PopularRecyclerView()
        onRandomMealClicked()
        observeRandomMeal()

        HomeMVVM.getPopularItems()
        observePopularItems()
        PopularItemClick()

        HomeMVVM.getCategories()
        observeCategoryItems()

        CateGoryRecyclerView()
        onCategoryClick()
        onPopularitemLongClick()

        onSearchItemClicked()

    }
    private fun Dialogs(){
        dialog.setContentView(R.layout.progress_bar_layout)
        dialog.setCancelable(false)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun onSearchItemClicked() {
        binding.startSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searcgFragment)
        }
    }

    private fun onPopularitemLongClick() {
        PopularItemAdapter.onLongItemClick = { meals ->
            val mealBottomSheet = MealBottomSheetFragment.newInstance(meals.idMeal)
            mealBottomSheet.show(childFragmentManager , "Meals")
        }
    }

    fun onCategoryClick() {
        Categoryadapter.ItemClick = {category ->
           startNew(requireActivity(),MEAL_CATEGORY , category)
        }
    }

    fun startNew(context: Context,name : String,category: Category){
        val intent = Intent(context , CategoryMealsActivity::class.java)
        intent.putExtra(name , category.strCategory)
        context.startActivity(intent)
    }

    fun CateGoryRecyclerView(){
        binding.cateRec.apply {
            layoutManager = GridLayoutManager(context , 3 ,GridLayoutManager.VERTICAL , false)
            adapter = Categoryadapter
        }
    }

    private fun observeCategoryItems() {
        HomeMVVM.observercategoryMealLivedata().observe(viewLifecycleOwner , {
                categoryList->
                Categoryadapter.setCategory(categoryList = categoryList as ArrayList<Category>)

        })
    }

    fun PopularItemClick(){
        PopularItemAdapter.onItemClick ={meal->
            val intent = Intent(activity , MealActivity::class.java)
            intent.putExtra(MEAL_ID , meal.idMeal)
            intent.putExtra(MEAL_NAME , meal.strMeal)
            intent.putExtra(MEAL_THUMB , meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun PopularRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity , LinearLayoutManager.HORIZONTAL , false)
            adapter = PopularItemAdapter
        }
    }

    private fun observePopularItems() {
        HomeMVVM.observerCategoryMealLivedata().observe(viewLifecycleOwner , {
            mealList->
            PopularItemAdapter.setMeals(mealList =  mealList as ArrayList<CategoryMeals>)

        })
    }

    private fun onRandomMealClicked() {
        binding.imageView.setOnClickListener {
            if (::randomMeal.isInitialized) {
                val intent = Intent(activity, MealActivity::class.java)

                intent.putExtra(MEAL_ID , randomMeal.idMeal)
                intent.putExtra(MEAL_NAME , randomMeal.strMeal)
                intent.putExtra(MEAL_THUMB , randomMeal.strMealThumb)

                startActivity(intent)
            } else {
                Toast.makeText(activity, "Random meal is not available yet.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun observeRandomMeal() {
        Dialogs()
        HomeMVVM.observerRandomMealLivedata().observe(viewLifecycleOwner ,
            { meal->
                if (activity != null) {
                    Glide.with(requireActivity()).load(meal!!.strMealThumb).into(binding.imageView)
                }
                this.randomMeal = meal
                dialog.dismiss()
        })

    }

}