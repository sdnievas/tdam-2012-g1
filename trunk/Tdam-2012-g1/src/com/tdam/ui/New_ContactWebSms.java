package com.tdam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdam.Database.SingletonDB;
import com.tdam_2012_g1.R;

public class New_ContactWebSms extends Activity implements OnClickListener {

	private TextView nContactoWeb;
	private Button boton;
	public static String EXTRA_WEB_CONTACT_NAME = "web_name";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new__contact_web_sms);

		nContactoWeb = (TextView) findViewById(R.id.newWebUser_NombUser);
		boton = (Button) findViewById(R.id.newWebUser_btnNewUser);

		boton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_new__contact_web_sms,
		// menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		String Name = nContactoWeb.getText().toString();
		if (!SingletonDB.getInstance(getApplicationContext())
				.getDatabaseHelper().exiteContacto(Name)) {
			Intent intent = new Intent();
			intent.putExtra(EXTRA_WEB_CONTACT_NAME, Name);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();
		} else {
			Toast t = Toast.makeText(getApplicationContext(),
					"Ya existe un contacto con el nombre web",
					Toast.LENGTH_SHORT);

			t.show();
		}

	}
}
