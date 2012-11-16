package com.tdam_2012_g1.suport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConexionInfo {

	private Context context;
	
	public static final int NO_INTERNET_CONNECTION = 0;
	public static final int MOBILE_CONNECTION = 1;
	public static final int WIFI_CONNECTION = 2;

	public ConexionInfo(Context context) {
		this.context = context;
	}

	public boolean isInternetConnectionAvailable() {
		ConnectivityManager connectivity;
		NetworkInfo wifiInfo, mobileInfo;

		connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		mobileInfo = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		return (wifiInfo.isConnected() || mobileInfo.isConnected());
	}

}
