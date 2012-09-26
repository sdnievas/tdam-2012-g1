package com.tdam_2012_g1;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preference_historial extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.historial_preference);
	}
}
