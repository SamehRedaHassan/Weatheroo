package com.iti.java.weatheroo.add_alarm.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButton
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModel
import com.iti.java.weatheroo.alerts.view_model.AlertsViewModelFactory
import com.iti.java.weatheroo.databinding.FragmentAddAlarmBinding
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AddAlarmFragment : Fragment() {
    var binding: FragmentAddAlarmBinding? = null
    private val myCalendar = Calendar.getInstance()
    private lateinit var navController: NavController
    private lateinit var viewModel: AlertsViewModel
    lateinit var startDateTxtView: EditText
    lateinit var endDateTxtView: EditText
    lateinit var alarmTime: EditText
    lateinit var reasonText: EditText
    lateinit var switch: Switch
    lateinit var addBtn: MaterialButton
    var alertTime: Long = 0
    var startDate: Long = 0
    var endDate: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl(
                    requireContext(), RetrofitService.getInstance(),
                    LocalSourceImpl(requireContext())
                ), requireContext()
            )
        ).get(AlertsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
            this,
            AlertsViewModelFactory(
                RepositoryImpl(
                    requireContext(), RetrofitService.getInstance(),
                    LocalSourceImpl(requireContext())
                ), requireContext()
            )
        ).get(AlertsViewModel::class.java)
        binding = FragmentAddAlarmBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_add_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        startDateTxtView = view.findViewById(R.id.startDateid)
        endDateTxtView = view.findViewById(R.id.endDateid)
        alarmTime = view.findViewById(R.id.alertTimeTxtView)
        reasonText = view.findViewById(R.id.reasonTxtView)
        switch = view.findViewById(R.id.enableDialogSwitch)
        addBtn = view.findViewById(R.id.addBtn)

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
        alarmTime.setOnClickListener {

            TimePickerDialog(
                requireContext(),
                12,
                TimePickerDialog.OnTimeSetListener { viewTimePicker, hour, minute ->
                    alertTime = timeToSeconds(hour, minute)
                    alarmTime.setText("$hour:$minute")
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY ,hour)
                    calendar.set(Calendar.MINUTE ,minute)
                    alertTime = calendar.timeInMillis
                },
                0, 0,
                true
            ).show()
        }

        startDateTxtView.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                    var startDateSt = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    startDate = dateToLong(startDateSt)
                    startDateTxtView.setText(startDateSt)
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR ,year)
                    calendar.set(Calendar.MONTH ,monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH ,dayOfMonth)
                    startDate = calendar.timeInMillis

                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }


        endDateTxtView.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { d_: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                    var endDateSt = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    endDate = dateToLong(endDateSt)
                    endDateTxtView.setText(endDateSt)
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR ,year)
                    calendar.set(Calendar.MONTH ,monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH ,dayOfMonth)
                    endDate = calendar.timeInMillis
                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }

        addBtn.setOnClickListener {
            val alertObj =
                MyAlert(startDate, endDate, alertTime, switch.isChecked, reasonText.text.toString())
            viewModel.addAlarm(alertObj)
            Toast.makeText(requireContext(), "added Successfully", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }

    }

    fun timeToSeconds(hour: Int, min: Int): Long {
        return (((hour * 60 + min) * 60) - 7200).toLong()
    }

    fun dateToLong(date: String?): Long {
        val f = SimpleDateFormat("dd-MM-yyyy")
        var milliseconds: Long = 0
        try {
            val d = f.parse(date)
            milliseconds = d.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return milliseconds / 1000
    }
}