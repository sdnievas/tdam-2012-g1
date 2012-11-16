package com.tdam_2012_g1;


import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Settings_activity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_main);
	}



    


}
