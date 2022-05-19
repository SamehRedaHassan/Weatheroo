package com.iti.java.weatheroo.alerts.view

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModel
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModelFactory
import com.iti.java.weatheroo.alerts.view_model.DeleteDelegate
import com.iti.java.weatheroo.databinding.FragmentAlertsBinding
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl
import com.iti.java.weatheroo.utils.Utils

class AlertsFragment : Fragment() ,DeleteDelegate{
    var binding: FragmentAlertsBinding? = null
    private lateinit var alertsRecyclerView: RecyclerView
    private lateinit var alertsAdapter:AlertsAdapter
    private lateinit var navController: NavController
    private lateinit var viewModel: AlertsViewModel
    private lateinit var addBtn   : FloatingActionButton
    var myAlerts: List<MyAlert>  = ArrayList<MyAlert>()
    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl.getInstance(requireContext(), retrofitService,
                    LocalSourceImpl(requireContext())
                ),requireContext())
        ).get(AlertsViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        viewModel =  ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl.getInstance(requireContext(), retrofitService,
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
                RepositoryImpl.getInstance(requireContext(), retrofitService,
                    LocalSourceImpl(requireContext())
                ),requireContext())
        ).get(AlertsViewModel::class.java)
        binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
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


    @RequiresApi(Build.VERSION_CODES.M)
    private fun configureUI() {
        //  dailyTemperatureRecyclerView = binding!!.todayWeatherRecycler
        // weeklyTemperatureRecyclerView = binding!!.nextWeekRecycler
      //  alertsRecyclerView = binding!!.alertsRecycler
        addBtn.setOnClickListener{
            Log.i("BOOOOM", "configureUI: ")


            if (Utils.getIsFirstTimeForAddAlarm(requireContext())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkDrawOverlayPermission()
                    Utils.setIsFirstTimeAddAlarm(requireContext(),false)
                } else {
                    navController.navigate(R.id.addAlarmFragment)
                }
            } else {
                navController.navigate(R.id.addAlarmFragment)
            }
        }
        alertsRecyclerView.setHasFixedSize(true)
        //adapters
        alertsAdapter = AlertsAdapter(this.requireContext(), myAlerts,this)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkDrawOverlayPermission() {
        // Check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(requireContext())) {
            // if not construct intent to request permission
            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            alertDialogBuilder.setTitle("Alert")
                .setMessage("by adding alarm the app may draw over other apps")
                .setPositiveButton("Okay") { dialog: DialogInterface, _: Int ->
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + requireActivity().packageName)
                    )
                    // request permission via start activity for result
                    startActivityForResult(intent, 1)
                    //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
                    dialog.dismiss()

                }.setNegativeButton(
                    "No"
                ) { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }.show()
        }
    }

    override fun deleteAlarm(myAlert: MyAlert) {
        viewModel.deleteAlarm(myAlert)
    }
}

