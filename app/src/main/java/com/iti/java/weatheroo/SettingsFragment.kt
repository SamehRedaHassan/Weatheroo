package com.iti.java.weatheroo.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iti.java.weatheroo.databinding.FragmentSettingsBinding
import com.iti.java.weatheroo.map.view.MapsActivity
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils

class SettingsFragment : Fragment() {
    var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyOldSettings()
    }

    private fun applyOldSettings() {
        when (Utils.getCurrentUnit(requireContext())) {
            Constants.METRIC -> binding!!.radioBtnMetric.isChecked = true
            Constants.IMPERIAL ->  binding!!.radioBtnImperial.isChecked = true
            Constants.STANDARD ->  binding!!.radioBtnStandard.isChecked = true
        }

        when (Utils.getCurrentLang(requireContext())) {
            "ar" -> binding!!.radioBtnArabic.isChecked = true
            "en" -> binding!!.radioBtnEnglish.isChecked = true
        }
    }

    private fun setupListeners(){
        binding!!.groupLanguage.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding!!.radioBtnEnglish.id -> {
                    Utils.saveCurrentLang(Constants.ENGLISH_LANGUAGE, requireContext())
                }
                binding!!.radioBtnArabic.id -> {
                    Utils.saveCurrentLang(Constants.Arabic_LANGUAGE, requireContext())
                }
            }
        }

        binding!!.groupLocation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding!!.radioBtnMap.id -> {
                    Utils.setUsingGPS(requireContext(),false)
                    val intent = Intent(requireContext() , MapsActivity::class.java)
                    intent.putExtra(Constants.SOURCE_FRAGMENT , Constants.SETTINGS)
                    startActivity(intent)
                }
                binding!!.radioBtnGps.id -> {
                    //Toast.makeText(context, "gps", Toast.LENGTH_SHORT).show()
                    Utils.setUsingGPS(requireContext(),true)
                    saveCurrentGPSLocation()
                }
            }
        }
        binding!!.groupUnits.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding!!.radioBtnImperial.id  -> {
                    Utils.setCurrentUnit(requireContext(),Constants.IMPERIAL)

                }
                binding!!.radioBtnStandard.id -> {
                    Utils.setCurrentUnit(requireContext(),Constants.STANDARD)
                }

                binding!!.radioBtnMetric.id -> {
                    Utils.setCurrentUnit(requireContext(),Constants.METRIC)
                }
            }
        }
    }

    private fun saveCurrentGPSLocation() {
        TODO("Not yet implemented")
    }


}