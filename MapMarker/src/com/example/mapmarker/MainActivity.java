package com.example.mapmarker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	GoogleMap gmap;
	Boolean isgpsenable,isnetworkenable;
	android.location.LocationListener ll;
	Double lat,lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView txt_val=(TextView)findViewById(R.id.txt_val);
		ll=new MyLocationListener();
		
		if (gmap == null) {
			MapFragment frag = (MapFragment) getFragmentManager().findFragmentById(R.id.map_frag);
			gmap = frag.getMap();
		}
		
		LocationManager manager=(LocationManager) getSystemService(LOCATION_SERVICE);
		isgpsenable=manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isnetworkenable=manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if(isgpsenable)
		{
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		}
		else
		{
			Toast.makeText(MainActivity.this, "gps provider not available", 1).show();
		}
		Location loc=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(loc!=null)
		{
			lat=loc.getLatitude();
			lng=loc.getLongitude();
			Toast.makeText(MainActivity.this, "gps lat"+lat+"lng"+lng, 1).show();
		}
		else
		{
			if(isnetworkenable)
			{
			manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
			}
			else
			{
				Toast.makeText(MainActivity.this, "network provider not available", 1).show();
			}
			loc=manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(loc!=null)
			{
			lat=loc.getLatitude();
			lng=loc.getLongitude();
			Toast.makeText(MainActivity.this, "network lat"+lat+"lng"+lng, 1).show();
			}
			else
			{
				Toast.makeText(MainActivity.this, "location null", 1).show();
			}
		}
		
//		MarkerOptions marker = new MarkerOptions().position(new LatLng(17.123212, 79.235478)).title("Title");
//		MarkerOptions marker = new MarkerOptions().position(new LatLng(lat,lng)).title("Current Location");
//		
//		CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(12).build();
//		gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//		gmap.addMarker(marker);
		
		Geocoder geocoder=new Geocoder(MainActivity.this, Locale.ENGLISH);
		try {
			List<Address> addresslist=geocoder.getFromLocation(lat, lng, 1);
			Address address=addresslist.get(0);
			StringBuilder straddress=new StringBuilder();
			for(int i=0;i<address.getMaxAddressLineIndex();i++)
			{
				straddress.append(address.getAddressLine(i)).append("\n");
			}
			txt_val.setText(straddress);
			MarkerOptions marker = new MarkerOptions().position(new LatLng(lat,lng)).title("Current Location"+straddress);
			
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(12).build();
			gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			gmap.addMarker(marker);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class MyLocationListener implements android.location.LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

}
