package com.slai.cmarms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.preference.*
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
        Log.d(SettingsFragment::class.java.simpleName, "${PrefUtils.getDayNightMode(context!!)}")
        val dayNightPref = findPreference<ListPreference>("theme")
        dayNightPref.setOnPreferenceChangeListener { preference, newValue ->
            val current = PrefUtils.getDayNightMode(context!!)
            Log.d(SettingsFragment::class.java.simpleName, "$newValue")
            val dayNightMode = PrefUtils.DayNightMode.valueOf(newValue as String)
            PrefUtils.setDayNightMode(context!!, dayNightMode)
            if(current != dayNightMode) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            true
        }
        dayNightPref.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        val layoutFeed = findPreference<ListPreference>("layout")
        layoutFeed.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        layoutFeed.setOnPreferenceChangeListener { preference, newValue ->
            Log.d(SettingsFragment::class.java.simpleName, "$newValue")
            val dayNightMode = PrefUtils.LayoutStyle.valueOf(newValue as String)
            PrefUtils.setLayoutStyle(context!!, dayNightMode)
            true
        }

        val reset = findPreference<PreferenceScreen>("reset")
        reset.setOnPreferenceClickListener {
            viewModel.clearPosts()
            GlobalScope.launch(Dispatchers.IO) {
                viewModel.reset(context!!)
                Snackbar.make(nav_container.getChildAt(0), "Reset Query and Posts", Snackbar.LENGTH_SHORT).show()
            }
            true
        }

        val cardWrapped = findPreference<SwitchPreference>("card_wrapped")
        cardWrapped.summaryProvider = object : Preference.SummaryProvider<SwitchPreference> {
            override fun provideSummary(preference: SwitchPreference?): CharSequence {
                return if(preference!!.isChecked) getString(R.string.settings_summary_card_wrapped_on)
                        else getString(R.string.settings_summary_card_wrapped_off)
            }

        }
    }
}