package com.example.videoplayer.data;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.videoplayer.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_preferences);
    }
}
