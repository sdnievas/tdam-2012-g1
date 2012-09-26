package com.tdam_2012_g1;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class Settings_activity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.contacts_preference);
	}



    


}
