package com.iti.java.weatheroo.favourite.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.databinding.FragmentFavouritesBinding
import com.iti.java.weatheroo.model.CurrentWeatherModel
import java.util.ArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.favourite.view_model.FavouritesViewModel
import com.iti.java.weatheroo.favourite.view_model.FavouritesViewModelFactory
import com.iti.java.weatheroo.home.view_model.HomeViewModel
import com.iti.java.weatheroo.home.view_model.HomeViewModelFactory
import com.iti.java.weatheroo.map.view.MapsActivity
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.Repository.Repository
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl
import com.iti.java.weatheroo.utils.Constants


class FavouritesFragment : Fragment() , NavigationDelegate {
    var binding: FragmentFavouritesBinding? = null
    private lateinit var favouritesPlacesRecyclerView: RecyclerView
    private lateinit var favouritesPlacesAdapter: FavouritesPlacesAdapter
    private lateinit var navController: NavController
    var favouritePlaces: List<FavouriteLocation> = ArrayList<FavouriteLocation>()
    private lateinit var favViewModel: FavouritesViewModel
    private val retrofitService = RetrofitService.getInstance()


    private lateinit var favouriteViewModelFactory : FavouritesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

       // return inflater.inflate(R.layout.fragment_favourites, container, false)

//        navController = activity?.findNavController(R.id.frag) ?: navController
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        favouritesPlacesAdapter.notifyDataSetChanged()


        favouriteViewModelFactory = FavouritesViewModelFactory(
            RepositoryImpl.getInstance(requireContext(), retrofitService, LocalSourceImpl(requireContext())), requireContext())
        favViewModel =  ViewModelProvider(
                    this,
            FavouritesViewModelFactory(RepositoryImpl.getInstance(requireContext(), retrofitService,LocalSourceImpl(requireContext())),requireContext())
        ).get(FavouritesViewModel::class.java)


        favViewModel.getFavouriteLocationsFromDatabase().observe(viewLifecycleOwner , { favLocations ->
            Log.e("AYWAAAAA", "observeeeeee")
            if(favLocations != null){
                favouritesPlacesAdapter.data = favLocations
                favouritesPlacesAdapter.notifyDataSetChanged()
            }
        })
        favouritesPlacesAdapter.notifyDataSetChanged()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouritesPlacesRecyclerView = view.findViewById(R.id.FavouritesRecyclerView)
        navController = NavHostFragment.findNavController(this)
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
        favouritesPlacesAdapter = FavouritesPlacesAdapter(this.requireContext(), favouritePlaces,this)

        favouritesPlacesRecyclerView.adapter = favouritesPlacesAdapter


        val mGridLayoutManager: GridLayoutManager

        mGridLayoutManager = GridLayoutManager(activity, 2)

        val manager: RecyclerView.LayoutManager = mGridLayoutManager

        favouritesPlacesRecyclerView.setLayoutManager(manager)

        favouritesPlacesAdapter.notifyDataSetChanged()
        binding?.btnAddFavId?.setOnClickListener{
            val myIntent = Intent(context, MapsActivity::class.java)
            myIntent.putExtra(Constants.SOURCE_FRAGMENT, Constants.FAVOURITES) //Optional parameters
            context?.startActivity(myIntent)
        }
    }

    override fun navigateToHome() {
        navController.navigate(R.id.homeFragment)
    }

    override fun deleteFav(location: FavouriteLocation) {
        favViewModel.deleteFavourite(location)
    }
}