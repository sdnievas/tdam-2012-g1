package com.tdam.ui;

import com.tdam_2012_g1.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preference_user extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.contacts_preference);
	}
}
