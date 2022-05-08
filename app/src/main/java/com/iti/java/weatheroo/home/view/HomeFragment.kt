package com.iti.java.weatheroo.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
//import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.databinding.FragmentHomeBinding
import com.iti.java.weatheroo.home.adpters.DailyTemperatureAdapter
import com.iti.java.weatheroo.home.adpters.WeeklyTemperatureAdapter
import com.iti.java.weatheroo.home.view_model.HomeViewModel
import com.iti.java.weatheroo.home.view_model.HomeViewModelFactory
import com.iti.java.weatheroo.model.CurrentWeatherModel
import com.iti.java.weatheroo.model.DailyWeatherModel
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import kotlinx.coroutines.*
import java.util.ArrayList

class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    lateinit var viewModel: HomeViewModel
    private val retrofitService = RetrofitService.getInstance()

    private lateinit var dailyLayoutManager: LinearLayoutManager
    private lateinit var weekelyLayoutManager: LinearLayoutManager

    private lateinit var dailyTemperatureRecyclerView: RecyclerView
    private lateinit var weeklyTemperatureRecyclerView: RecyclerView
    private lateinit var dailyTemperatureAdapter: DailyTemperatureAdapter
    private lateinit var weeklyTemperatureAdapter: WeeklyTemperatureAdapter
   // private lateinit var navController: NavController
    var hourlyPredictions: List<CurrentWeatherModel> = ArrayList<CurrentWeatherModel>()
    var dailyPredictions: List<DailyWeatherModel> = ArrayList<DailyWeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
               binding = FragmentHomeBinding.inflate(inflater, container, false)

  //      navController = findNavController(activity!!, R.id.frag)
       return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initData() {


        var job : Job? = null
        lifecycle.coroutineScope.launch {
            job = CoroutineScope(Dispatchers.IO).launch {
                Log.i("UUU", "initData1: ")
                viewModel.getCurrentWeather()
                withContext(Dispatchers.Main){

                    //adapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun configureUI() {
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(RepositoryImpl(requireContext(), retrofitService))
        ).get(HomeViewModel::class.java)

      dailyTemperatureRecyclerView = binding!!.todayWeatherRecycler
        weeklyTemperatureRecyclerView = binding!!.nextWeekRecycler
        dailyTemperatureRecyclerView.setHasFixedSize(true)
        weeklyTemperatureRecyclerView.setHasFixedSize(true)
        //adapters
        dailyTemperatureAdapter = DailyTemperatureAdapter(this.requireContext(), hourlyPredictions)
        weeklyTemperatureAdapter = WeeklyTemperatureAdapter(this.requireContext(), dailyPredictions)

        dailyTemperatureRecyclerView.adapter = dailyTemperatureAdapter
        weeklyTemperatureRecyclerView.adapter = weeklyTemperatureAdapter
        viewModel.hourly.observe(this, Observer {
            Log.i("TAG", "onCreatttttte: $it")
            binding!!.locationTextView.text = "AAAAAA"
            binding!!.temperatureTextView.text = viewModel.currentWeather.temp.toString()
            binding!!.statusTxtView.text = viewModel.currentWeather.weather.get(0).main
            binding!!.windSpeedTextView.text = viewModel.currentWeather.windSpeed.toString()
            binding!!.humidityTxtView.text = viewModel.currentWeather.humidity.toString()
            binding!!.PressureTxtView.text = viewModel.currentWeather.pressure.toString()



            dailyTemperatureAdapter.setDailyWeather(it)
        })
        viewModel.daily.observe(this, Observer {
            Log.i("TAG", "onCreatttttte: $it")
            weeklyTemperatureAdapter.setWeekelyWeather(it)
        })

        dailyLayoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        weekelyLayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        dailyTemperatureRecyclerView.setLayoutManager(dailyLayoutManager)
        weeklyTemperatureRecyclerView.setLayoutManager(weekelyLayoutManager)

        dailyTemperatureAdapter.notifyDataSetChanged()
        weeklyTemperatureAdapter.notifyDataSetChanged()


    }
}