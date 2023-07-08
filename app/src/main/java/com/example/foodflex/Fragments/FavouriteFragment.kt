package com.example.foodflex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodflex.Adapters.FavouriteMealAdapter
import com.example.foodflex.MainActivity
import com.example.foodflex.R
import com.example.foodflex.databinding.FragmentFavouriteBinding
import com.example.foodflex.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var favAdapter : FavouriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preRec()
        onservrFavourite()

        val itemTouchHealper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favAdapter.differ.currentList[position])

                Snackbar.make(requireView() , "Meal Deleted" , Snackbar.LENGTH_LONG).show()
            }

        }
        ItemTouchHelper(itemTouchHealper).attachToRecyclerView(binding.favRec)
    }

    private fun preRec() {
        favAdapter = FavouriteMealAdapter()
        binding.favRec.apply {
            layoutManager = GridLayoutManager(context , 2 , GridLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
    }

    private fun onservrFavourite() {
        viewModel.observerFavoutiteMealLiveData().observe(requireActivity() , Observer {meals->
            favAdapter.differ.submitList(meals)
        })
    }

}