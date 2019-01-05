package com.slai.cmarms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.google.android.material.snackbar.Snackbar
import com.slai.cmarms.viewmodel.CmarmsViewModel
import kotlinx.android.synthetic.main.fragment_nav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {

    val viewModel by lazy { ViewModelProviders.of(activity!!).get(CmarmsViewModel::class.java)}

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        Log.d(SettingsFragment::class.java.simpleName, "${Utils.getDayNightMode(context!!)}")
        val dayNightPref = findPreference<ListPreference>("theme")
        dayNightPref.setOnPreferenceChangeListener { preference, newValue ->
            val current = Utils.getDayNightMode(context!!)
            Log.d(SettingsFragment::class.java.simpleName, "$newValue")
            val dayNightMode = Utils.DayNightMode.valueOf(newValue as String)
            Utils.setDayNightMode(context!!, dayNightMode)
            if(current != dayNightMode) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            true
        }
        dayNightPref.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        val reset = findPreference<PreferenceScreen>("reset")
        reset.setOnPreferenceClickListener {
            viewModel.clearPosts()
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.reset(context!!)
                Snackbar.make(nav_container.getChildAt(0), "Reset Query and Posts", Snackbar.LENGTH_SHORT).show()
            }
            true
        }
    }




}