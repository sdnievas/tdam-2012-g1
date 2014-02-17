package com.tdam.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdam.Class.Usuario;
import com.tdam.Database.SingletonDB;
import com.tdam.ServicioWeb.WebService;
import com.tdam.ServicioWeb.WebServiceInfo;
import com.tdam.Suport.ConexionInfo;
import com.tdam.Suport.Notificacion;
import com.tdam_2012_g1.R;

public class New_User extends Activity implements OnClickListener {
	// vista
	private Button boton;
	private EditText nomuser;
	private EditText password;
	private EditText Repassword;
	private EditText Email;

	private static final String DIALOG_ERROR = "Error";
	private static final String DIALOG_MSJ = "No hay conexión a ocurrido un error";
	private static final String DIALOG_BTN = "Aceptar";
	private static final String USER_NAME_ERROR = "El Nombre debe contener 6 o mas caracteres";
	private static final String USER_PASS_ERROR = "El Password debe contener 6 o mas caracteres y debe conicidir las 2 veces";

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);

		nomuser = (EditText) findViewById(R.id.new_NombUser);
		password = (EditText) findViewById(R.id.new_PassUser);
		Repassword = (EditText) findViewById(R.id.new_RePassUser);
		Email = (EditText) findViewById(R.id.new_Email);

		boton = (Button) findViewById(R.id.new_btnNewUser);
		boton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/*
	 * Metodo onClick del boton de la activity comprueba que los valores
	 * introducidos son correctos y si lo son llama al metodo Registrar_User
	 */
	@Override
	public void onClick(View v) {

		Usuario oUser = new Usuario();

		// Control de Nombre de Usuario
		if (nomuser.getText().toString().getBytes().length >= 6) {
			oUser.set_nombre(nomuser.getText().toString());
		} else {
			Toast.makeText(this, USER_NAME_ERROR, 100);
		}

		// Control de password
		if (password.getText().toString()
				.equals(Repassword.getText().toString())
				&& password.getText().toString().getBytes().length >= 6) {
			oUser.set_contraseña(password.getText().toString());
		} else {
			Toast.makeText(this, USER_PASS_ERROR, 100);
		}

		oUser.set_mail(Email.getText().toString());

		// llama metodo de registracion de usuarios
		Registrar_User(oUser);

	}

	/*
	 * Metodo privado para la registracion de los usuarios llama a la tarea para
	 * la creacion de de un nuevo usuario web
	 * 
	 * @param Usuario parUsuario nesesario para la tarea
	 */
	private void Registrar_User(Usuario parUsuario) {

		// revisa conexion a internet
		ConexionInfo coninf = new ConexionInfo(this);
		boolean hayconexionInternet = coninf.isInternetConnectionAvailable();

		if (hayconexionInternet) {
			if (validarDatosIngresados() == true) {
				// si hay conexion registra el usuario
				RegisterUserTask _initTask = new RegisterUserTask(parUsuario,
						this);
				_initTask.execute();

			}
		} else {
			Dialog dialogo = null;
			dialogo = createAlertDialog();
			dialogo.show();
		}
		// vuelve a la pantalla anterior de inicio
		Intent intent = new Intent(this, Inicio.class);
		startActivity(intent);

	}

	private Dialog createAlertDialog() {
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(R.drawable.image_notification).setTitle(DIALOG_ERROR)
				.setPositiveButton(DIALOG_BTN, null).setMessage(DIALOG_MSJ)
				.create();
		return dialog;
	}

	/*
	 * Valida los datos del usuario para saber si el mismo ha introducido los
	 * datos correctos
	 */
	public boolean validarDatosIngresados() {
		if (this.nomuser.getText().toString().trim().length() == 0
				|| this.password.getText().toString().trim().length() == 0
				|| this.Repassword.getText().toString().trim().length() == 0
				|| this.Email.getText().toString().trim().length() == 0) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setIcon(R.drawable.image_notification)
					.setTitle(DIALOG_ERROR).setPositiveButton(DIALOG_BTN, null)
					.setMessage("Datos Incompletos").create();
			dialog.show();
			return false;
		}
		if (!this.password.getText().toString()
				.equals(this.Repassword.getText().toString())) {
			Dialog dialog = new AlertDialog.Builder(this)
					.setIcon(R.drawable.image_notification)
					.setTitle(DIALOG_ERROR).setPositiveButton(DIALOG_BTN, null)
					.setMessage("Las passwords no concuerdan").create();
			dialog.show();
			return false;
		}
		return true;
	}

	/*
	 * Clase que realiza una tarea asincrona de registar un nuevousuario en el
	 * dispositivo
	 */
	protected class RegisterUserTask extends
			AsyncTask<String, Integer, WebServiceInfo> {

		Usuario user;
		Context context;

		public RegisterUserTask(Usuario user, Context context) {
			this.user = user;
			this.context = context;
		}

		@Override
		protected WebServiceInfo doInBackground(String... params) {

			WebServiceInfo result = null;

			try {
				WebService web = new WebService(user.get_nombre(),
						user.get_contraseña(),this.context);
				result = web.registerUser();
			} catch (Exception e) {
				e.toString();
			}

			if (result != null && WebServiceInfo.SUCCESS == result.getCode()) {
				// registra un nuevo usuario en la base de datos si el contacto
				// se ha registrado correctamente
				SingletonDB.getInstance(context).getDatabaseHelper()
						.addUsuario(user);
				// System.out.println(SingletonDB.getInstance(context).getDatabaseHelper().getAllUsuarios().toString()+"ACA");
			}
			return result;
		}

		@Override
		protected void onPostExecute(WebServiceInfo result) {
			super.onPostExecute(result);
			if (result.getCode() == -1 || result.getCode() == 0
					|| result.getCode() == 2) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(context)
						.setIcon(R.drawable.image_notification);
				builder1.setTitle(context.getString(R.string.dialog_info));
				builder1.setMessage(R.string.dialog_error);
				builder1.setPositiveButton(R.string.dialog_btn, null);
				builder1.show();
			}
			Notificacion noti = new Notificacion(context, result, 1);
			noti.notificionMensajes();
		}

	}

}
