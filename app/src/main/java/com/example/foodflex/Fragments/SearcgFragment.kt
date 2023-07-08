package com.example.foodflex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodflex.Adapters.FavouriteMealAdapter
import com.example.foodflex.MainActivity
import com.example.foodflex.R
import com.example.foodflex.databinding.FragmentSearcgBinding
import com.example.foodflex.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearcgFragment : Fragment() {

    private lateinit var binding: FragmentSearcgBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searcgAdapter: FavouriteMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearcgBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searcgAdapter = FavouriteMealAdapter()
        preapirRec()

        binding.searchImage.setOnClickListener {
            searchItem()
        }

        onserverSearchViewModel()


        var job : Job? = null
        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(it.toString())
            }
        }



    }

    private fun onserverSearchViewModel() {
        viewModel.observerSearchMealLivedata().observe(viewLifecycleOwner , Observer { meals->
            searcgAdapter.differ.submitList(meals)
        })
    }

    private fun searchItem() {
        val searchQuery = binding.searchEditText.text.toString()
        if (searchQuery!=null){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun preapirRec() {
        binding.searchRec.apply {
            layoutManager = GridLayoutManager(context , 2 , GridLayoutManager.VERTICAL , false)
            adapter = searcgAdapter
        }
    }

}