package com.example.maptask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	GoogleMap gmap;
	Boolean isgpsenable,isnetworkenable;
	android.location.LocationListener ll;
	Double cur_lat,cur_lng;
	TextView tvtitle,tvLat,tvLng;
	Geocoder geocoder;
	ArrayList<StringBuilder> addrlist=new ArrayList<StringBuilder>();
	private static final LatLng BASHEERBAGH = new LatLng(17.3998, 78.4766);
	private static final LatLng AMEERPET = new LatLng(17.4368, 78.4439);
	private static final LatLng SEC = new LatLng(17.4500, 78.5000);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView txt_val=(TextView)findViewById(R.id.txt_val);
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
			cur_lat=loc.getLatitude();
			cur_lng=loc.getLongitude();
			Toast.makeText(MainActivity.this, "gps lat"+cur_lat+"lng"+cur_lng, 1).show();
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
				cur_lat=loc.getLatitude();
				cur_lng=loc.getLongitude();
			Toast.makeText(MainActivity.this, "network lat"+cur_lat+"lng"+cur_lng, 1).show();
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
		
		//places api
		
		
		
		
		
		//geo
		 geocoder=new Geocoder(MainActivity.this, Locale.ENGLISH);
		
			StringBuilder str=getAddress(cur_lat, cur_lng);
			addrlist.add(str);
			//			List<Address> addresslist=geocoder.getFromLocation(cur_lat, cur_lng, 1);
//			Address address=addresslist.get(0);
//			StringBuilder straddress=new StringBuilder();
//			for(int i=0;i<address.getMaxAddressLineIndex();i++)
//			{
//				straddress.append(address.getAddressLine(i)).append("\n");
//				Toast.makeText(getApplicationContext(), ""+i+"--->"+address.getAddressLine(i), 1).show();
//			}
//			txt_val.setText(str);
			MarkerOptions marker = new MarkerOptions().position(new LatLng(cur_lat,cur_lng)).title("0");
			MarkerOptions marker2 = new MarkerOptions().position(AMEERPET).title("1");
			StringBuilder str2=getAddress(AMEERPET.latitude, AMEERPET.longitude);
			addrlist.add(str2);
//			txt_val.append("\n");
//			txt_val.append(str);
			MarkerOptions marker3 = new MarkerOptions().position(SEC).title("2");
			StringBuilder str3=getAddress(SEC.latitude, SEC.longitude);
			addrlist.add(str3);
//			txt_val.append("\n");
//			txt_val.append(str);
			
			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(cur_lat, cur_lng)).zoom(12).build();
			gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
			gmap.addMarker(marker);
			gmap.addMarker(marker2);
			gmap.addMarker(marker3);
			
			gmap.setInfoWindowAdapter(new InfoWindowAdapter() {
				 
		            // Use default InfoWindow frame
		            @Override
		            public View getInfoWindow(Marker arg0) {
		                return null;
		            }
		 
		            // Defines the contents of the InfoWindow
		            @Override
		            public View getInfoContents(Marker arg0) {
		 
		            	
		                View v = getLayoutInflater().inflate(R.layout.info_window, null);
		                LatLng latLng = arg0.getPosition();
		                
		                tvtitle = (TextView) v.findViewById(R.id.title);
		                tvLat = (TextView) v.findViewById(R.id.address1);
		                tvLng = (TextView) v.findViewById(R.id.address2);
//		                tvtitle.setText("Title: Title");
//		                tvLat.setText("Latitude:" + latLng.latitude);
//		                tvLng.setText("Longitude:"+ latLng.longitude);
		                // Returning the view containing InfoWindow contents
		                txt_val.setText("");
//		                for(int i=0;i<addrlist.size();i++)
//		                {
//		                	txt_val.append(""+addrlist.get(i).toString());
//		                }
		                Toast.makeText(getApplicationContext(),"size of addrlist"+addrlist.size(),1).show();
		                
		                if(arg0.getTitle().equals("1"))
		                {
		                	tvtitle.setText("Basheerbagh");
		                	tvLat.setText(""+addrlist.get(0).toString());
//		                	 tvtitle.setText("Title"+addrlist.get(0).toString());
		                }
		                else if(arg0.getTitle().equals("2"))
		                {
		                	tvtitle.setText("Ameerpet");
		                	tvLat.setText(""+addrlist.get(1).toString());
//		                	 tvtitle.setText("Title"+addrlist.get(1).toString());
		                }
		                else
		                {
		                	tvtitle.setText("Secunderabad");
		                	tvLat.setText(""+addrlist.get(2).toString());
//		                	 tvtitle.setText("Title"+addrlist.get(2).toString());
		                }
		                return v;
		            }
		        });
			
		

	}
	public StringBuilder getAddress(double lat,double lng)
	{
		StringBuilder straddress=null;
		
		try {
			List<Address> addresslist=geocoder.getFromLocation(lat, lng, 1);
			Address address=addresslist.get(0);
			 straddress=new StringBuilder();
			for(int i=0;i<address.getMaxAddressLineIndex();i++)
			{
				straddress.append(address.getAddressLine(i)).append("\n");
//				st=straddress.toString();
				Toast.makeText(getApplicationContext(), ""+i+"--->"+address.getAddressLine(i), 1).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return straddress;
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