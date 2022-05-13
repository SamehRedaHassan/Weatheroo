package com.iti.java.weatheroo.alarms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.alerts.view.AlertsAdapter
import com.iti.java.weatheroo.databinding.FragmentAlertsBinding
import com.iti.java.weatheroo.model.AlertModel

class AlertsFragment : Fragment() {
    var binding: FragmentAlertsBinding? = null
    private lateinit var alertsRecyclerView: RecyclerView
    private lateinit var alertsAdapter:AlertsAdapter
    private lateinit var navController: NavController
    var alerts: List<AlertModel>  = ArrayList<AlertModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alertsRecyclerView = view.findViewById(R.id.alertsRecycler)

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
        alertsRecyclerView.setHasFixedSize(true)
        //adapters
        alertsAdapter = AlertsAdapter(this.requireContext(), alerts)

        alertsRecyclerView.adapter = alertsAdapter


        val manager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        alertsRecyclerView.setLayoutManager(manager)

        alertsAdapter.notifyDataSetChanged()


    }



}