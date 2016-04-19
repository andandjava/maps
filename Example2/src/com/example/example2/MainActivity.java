package com.example.example2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	
	private GoogleMap mMap;
	private OnInfoWindowElemTouchListener infoButtonListener,infoButtonListener1,infoButtonListener2;
	private ViewGroup infoWindow;
	CustomLayout mapWrapperLayout;
	SupportMapFragment mapFragment;
	private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
	private HashMap<Marker, MyMarker> mMarkersHashMap;
	ImageView markerIcon;
	TextView markerLabel, markerLabel1, markerLabel2;
	Button markerLabelView,markerLabelView1,markerLabelView2;
	String map_info = "";
	static ArrayList<String> latlist=new ArrayList<String>();
	static ArrayList<String> lnglist=new ArrayList<String>();
	Geocoder geocoder;
//	geocoder=new Geocoder(MainActivity.this, Locale.ENGLISH);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMarkersHashMap = new HashMap<Marker, MyMarker>();
		mMyMarkersArray.add(new MyMarker("KANDAKURTHI", "NIZAMABAD", "kandakurthi",Double.parseDouble("18.750397"), Double.parseDouble("77.9738382")));
//		mMyMarkersArray.add(new MyMarker("DHARMAPURI", "Karimnagar","dharmapuri", Double.parseDouble("18.9548802"), Double.parseDouble("79.0901034")));
//		mMyMarkersArray.add(new MyMarker("BASARA", "Adilabad", "basara",Double.parseDouble("18.88017510"), Double.parseDouble("77.95387670")));
//		mMyMarkersArray.add(new MyMarker("MANGAPET", "WARANGAL","mangapet", Double.parseDouble("18.2500"), Double.parseDouble("80.5200")));
//		mMyMarkersArray.add(new MyMarker("BHADRACHALAM", "Khammam","bhadrachalam", Double.parseDouble("17.668791200"), Double.parseDouble("80.89359250")));
		
		setUpMap();  
		plotMarkers(mMyMarkersArray);
	}
	private void setUpMap() {
		// Do a null check to confirm that we have not already instantiated the map.
		
		if (mMap == null) {
			mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			
			// Getting GoogleMap object from the fragment
			mMap = mapFragment.getMap();
			mMap.clear();
			
			mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
					 marker.hideInfoWindow();
					marker.showInfoWindow();
					return true;
				}
			});
			mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick(Marker marker) {
					marker.showInfoWindow();
				}
			});
			this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.infowindow_layout, null);

			markerIcon = (ImageView) infoWindow.findViewById(R.id.marker_icon);
			markerLabel = (TextView) infoWindow.findViewById(R.id.marker_label);
			markerLabel1 = (TextView) infoWindow.findViewById(R.id.marker_label_1);
			markerLabel2 = (TextView) infoWindow.findViewById(R.id.marker_label_2);
			markerLabelView = (Button) infoWindow.findViewById(R.id.marker_label_view);
			markerLabelView1 = (Button) infoWindow.findViewById(R.id.marker_label_view1);
			markerLabelView2 = (Button) infoWindow.findViewById(R.id.marker_label_view2); 

			infoButtonListener = new OnInfoWindowElemTouchListener(markerLabelView) {
				@Override
				protected void onClickConfirmed(View v, Marker marker) {
//					MyMarker myMarker = mMarkersHashMap.get(marker);
//					System.out.println("data" + myMarker
//							+ markerLabelView.getTag().toString());
					if (markerLabelView.getTag().toString().contains("BHADRACHALAM")) {
//						SplashActivity.flg = 0;
//					startActivity(new Intent(MainActivity.this, InnerScreenActivity.class));	
					Toast.makeText(getApplicationContext(), "View BHADRACHALAM", Toast.LENGTH_SHORT).show();
						
					}
					else if (markerLabelView.getTag().toString().contains("BASARA")) {
						Toast.makeText(getApplicationContext(), "View BASARA", Toast.LENGTH_SHORT).show();
//						SplashActivity.flg = 1;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
					
					} else if (markerLabelView.getTag().toString().contains("DHARMAPURI")) {
						Toast.makeText(getApplicationContext(), "View DHARMAPURI", Toast.LENGTH_SHORT).show();
//						SplashActivity.flg = 2;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
					
					} else if (markerLabelView.getTag().toString().contains("MANGAPET")) {
						Toast.makeText(getApplicationContext(), "View MANGAPET", Toast.LENGTH_SHORT).show();
//						SplashActivity.flg = 3;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
					
					} else if (markerLabelView.getTag().toString().contains("KANDAKURTHI")) {
						Toast.makeText(getApplicationContext(), "View KANDAKURTHI", Toast.LENGTH_SHORT).show();
//						SplashActivity.flg = 4;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
					}
				}
			};
			
			infoButtonListener1 = new OnInfoWindowElemTouchListener(
					markerLabelView1) {
				@Override
				protected void onClickConfirmed(View v, Marker marker) {
//					MyMarker myMarker = mMarkersHashMap.get(marker);
//					System.out.println("data" + myMarker
//							+ markerLabelView.getTag().toString());
					if (markerLabelView.getTag().toString().contains("BHADRACHALAM")) {
//						SplashActivity.flg = 0;
					Toast.makeText(getApplicationContext(), "Bhanrachalam", Toast.LENGTH_SHORT).show();	
//					startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));	
						
					}else if (markerLabelView.getTag().toString().contains("BASARA")) {
//						SplashActivity.flg = 1;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Basara", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("DHARMAPURI")) {
//						SplashActivity.flg = 2;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Dharmapuri", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("MANGAPET")) {
//						SplashActivity.flg = 3;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Mangapet", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("KANDAKURTHI")) {
//						SplashActivity.flg = 4;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Kandakurthi", Toast.LENGTH_SHORT).show();	
					}
				}
			};
			
			infoButtonListener2 = new OnInfoWindowElemTouchListener(
					markerLabelView2) {
				@Override
				protected void onClickConfirmed(View v, Marker marker) {
//					MyMarker myMarker = mMarkersHashMap.get(marker);
//					System.out.println("data" + myMarker
//							+ markerLabelView.getTag().toString());
					if (markerLabelView.getTag().toString().contains("BHADRACHALAM")) {
//						SplashActivity.flg = 0;
					Toast.makeText(getApplicationContext(), "Send Bhanrachalam", Toast.LENGTH_SHORT).show();	
//					startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));	
						
					}else if (markerLabelView.getTag().toString().contains("BASARA")) {
//						SplashActivity.flg = 1;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Send Basara", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("DHARMAPURI")) {
//						SplashActivity.flg = 2;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Send Dharmapuri", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("MANGAPET")) {
//						SplashActivity.flg = 3;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Send Mangapet", Toast.LENGTH_SHORT).show();	
					
					} else if (markerLabelView.getTag().toString().contains("KANDAKURTHI")) {
//						SplashActivity.flg = 4;
//						startActivity(new Intent(GhatInfoActivity.this, InnerScreenActivity.class));
						Toast.makeText(getApplicationContext(), "Send Kandakurthi", Toast.LENGTH_SHORT).show();	
					}
				}
			};

			markerLabelView.setOnTouchListener(infoButtonListener);
			markerLabelView1.setOnTouchListener(infoButtonListener1);
			markerLabelView2.setOnTouchListener(infoButtonListener2);
		} else {
			Toast.makeText(this, "Unable to create Maps",Toast.LENGTH_SHORT).show();
		}
	}
	public void plotMarkers(ArrayList<MyMarker> markers) {
		if (markers.size() > 0) {
			try {
				mMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (MyMarker myMarker : markers) {
				// Create user marker with custom icon and other options
				MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
				markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
				markerOption.title("title");
				markerOption.snippet("snippet");
				//add layout containing image(marker) and textview showing title in the form of bitmap
				Marker currentMarker = mMap.addMarker(markerOption);
				currentMarker.hideInfoWindow();
				mMarkersHashMap.put(currentMarker, myMarker);
				LatLng centerCoord = new LatLng(myMarker.getmLatitude(),myMarker.getmLongitude());
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerCoord,6.3f));
				mapWrapperLayout = (CustomLayout)findViewById(R.id.map_relative_layout);
				mapWrapperLayout.init(mMap,		getPixelsFromDp(this, 39 + 20));

				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
					@Override
					public View getInfoWindow(Marker marker) {
						return null;
					}
					@Override
					public View getInfoContents(Marker marker) {
						infoButtonListener.setMarker(marker);
						infoButtonListener1.setMarker(marker);
						infoButtonListener2.setMarker(marker);
						mapWrapperLayout.setMarkerWithInfoWindow(marker,infoWindow);
						final MyMarker myMarker = mMarkersHashMap.get(marker);
						markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));
						markerLabel.setText(myMarker.getmLabel());
						markerLabelView.setTag(myMarker.getmLabel());
						markerLabel1.setText(myMarker.getmLabel1());
						markerLabel2.setText("Pushkar Ghat");
						return infoWindow;
					}
				});
				// mMap.addMarker(options);
			}
		} else {
			try {
				mMap.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static int getPixelsFromDp(Context context,	float dp) 
	{
		float density1 = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density1 + 0.5f);
	}
	private int manageMarkerIcon(String markerIcon) {
		if (markerIcon.equals("kandakurthi"))
			return R.drawable.kandakurthi;
		else if (markerIcon.equals("dharmapuri"))
			return R.drawable.dharmapuri;
		else if (markerIcon.equals("basara"))
			return R.drawable.basara;
		else if (markerIcon.equals("mangapet"))
			return R.drawable.hemachala_narasimha;
		else if (markerIcon.equals("bhadrachalam"))
			return R.drawable.bhadrachalam;
		else
			return R.drawable.bhadrachalam_icon1;
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
