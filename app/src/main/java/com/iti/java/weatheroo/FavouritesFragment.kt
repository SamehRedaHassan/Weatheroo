package com.iti.java.weatheroo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.databinding.FragmentFavouritesBinding
import com.iti.java.weatheroo.favourite.view.FavouritesPlacesAdapter
import com.iti.java.weatheroo.model.CurrentWeatherModel
import java.util.ArrayList
import androidx.recyclerview.widget.GridLayoutManager




class FavouritesFragment : Fragment() {
    var binding: FragmentFavouritesBinding? = null
    private lateinit var favouritesPlacesRecyclerView: RecyclerView
    private lateinit var favouritesPlacesAdapter: FavouritesPlacesAdapter
    private lateinit var navController: NavController
    var favouritePlaces: List<CurrentWeatherModel> = ArrayList<CurrentWeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        return inflater.inflate(R.layout.fragment_favourites, container, false)

//        navController = activity?.findNavController(R.id.frag) ?: navController
//        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouritesPlacesRecyclerView = view.findViewById(R.id.FavouritesRecyclerView)

        configureUI()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initData() {
    }


    private fun configureUI() {
        //  dailyTemperatureRecyclerView = binding!!.todayWeatherRecycler
        // weeklyTemperatureRecyclerView = binding!!.nextWeekRecycler
        favouritesPlacesRecyclerView.setHasFixedSize(true)
        //adapters
        favouritesPlacesAdapter = FavouritesPlacesAdapter(this.requireContext(), favouritePlaces)

        favouritesPlacesRecyclerView.adapter = favouritesPlacesAdapter


        val mGridLayoutManager: GridLayoutManager
// ...
// ...
        mGridLayoutManager = GridLayoutManager(activity, 2)

        val manager: RecyclerView.LayoutManager = mGridLayoutManager

        favouritesPlacesRecyclerView.setLayoutManager(manager)

        favouritesPlacesAdapter.notifyDataSetChanged()


    }




}