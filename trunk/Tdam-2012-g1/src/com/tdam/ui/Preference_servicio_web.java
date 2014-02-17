package com.tdam.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tdam_2012_g1.R;

public class Preference_servicio_web extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.servicio_web_preference);
	}
}
