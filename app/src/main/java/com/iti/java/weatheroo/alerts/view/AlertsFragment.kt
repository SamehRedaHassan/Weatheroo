package com.iti.java.weatheroo.alerts.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModel
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModelFactory
import com.iti.java.weatheroo.databinding.FragmentAlertsBinding
import com.iti.java.weatheroo.model.Alert
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl

class AlertsFragment : Fragment() {
    var binding: FragmentAlertsBinding? = null
    private lateinit var alertsRecyclerView: RecyclerView
    private lateinit var alertsAdapter:AlertsAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel: AlertsViewModel
    private lateinit var addBtn   : FloatingActionButton
    var alerts: List<Alert>  = ArrayList<Alert>()
    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl(requireContext(), retrofitService,
                    LocalSourceImpl(requireContext())
                ),requireContext())
        ).get(AlertsViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        viewModel =  ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl(requireContext(), retrofitService,
                    LocalSourceImpl(requireContext())
                ),requireContext())
        ).get(AlertsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =  ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl(requireContext(), retrofitService,
                    LocalSourceImpl(requireContext())
                ),requireContext())
        ).get(AlertsViewModel::class.java)
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        alertsRecyclerView = view.findViewById(R.id.alertsRecycler)
        addBtn = view.findViewById(R.id.addAlerrttBtn)

        configureUI()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initData() {
        viewModel.getAlarms()
    }


    private fun configureUI() {
        //  dailyTemperatureRecyclerView = binding!!.todayWeatherRecycler
        // weeklyTemperatureRecyclerView = binding!!.nextWeekRecycler
      //  alertsRecyclerView = binding!!.alertsRecycler
        addBtn.setOnClickListener{
            Log.i("BOOOOM", "configureUI: ")
            navController.navigate(R.id.addAlarmFragment)
        }
        alertsRecyclerView.setHasFixedSize(true)
        //adapters
        alertsAdapter = AlertsAdapter(this.requireContext(), alerts)
        alertsRecyclerView.adapter = alertsAdapter
        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        alertsRecyclerView.setLayoutManager(manager)
        viewModel.getAlarms().observe(viewLifecycleOwner , { alarms ->
            if(alarms != null){
                alertsAdapter.data = alarms
                alertsAdapter.notifyDataSetChanged()
            }
        })

        alertsAdapter.notifyDataSetChanged()

    }
}

