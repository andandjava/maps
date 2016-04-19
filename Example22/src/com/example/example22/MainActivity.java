package com.example.example22;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	ArrayList<GooglePlace> venuesList;
	
	static TextView txt;
	 Context con=MainActivity.this;

	static ArrayList<String> latlist=new ArrayList<String>();
	static ArrayList<String> lnglist=new ArrayList<String>();
	ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txt=(TextView)findViewById(R.id.txt);
		new googleplaces().execute();
	}

	private class googleplaces extends AsyncTask<View, Void, String> {

		String temp;
		@Override
		protected String doInBackground(View... urls) {
//			temp = makeCall("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=100&sensor=true&key=" + GOOGLE_KEY);
			temp = makeCall("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=17.3998,78.4766&radius=2000&types=restaurant&key=AIzaSyC-TMz3tOKw6wr40I1sSl-xL7gHv-DexQI");
			
			//print the call in the console
//			System.out.println("https://maps.googleapis.com/maps/api/place/search/json?location=" + latitude + "," + longtitude + "&radius=100&sensor=true&key=" + GOOGLE_KEY);
//			System.out.println("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longtitude + "&radius=100&sensor=true&key=" + GOOGLE_KEY);
			return "";
		}

		@Override
		protected void onPreExecute() {
			// we can start a progress bar here
		}

		@Override
		protected void onPostExecute(String result) {
			if (temp == null) {
				// we have an error to the call
				// we can also stop the progress bar
			} else {
				// all things went right
				// parse Google places search result
				venuesList = (ArrayList<GooglePlace>) parseGoogleParse(temp);
				System.out.println("venueslist"+venuesList);

				List<String> listTitle = new ArrayList<String>();

//				for (int i = 0; i < venuesList.size(); i++) {
//					// make a list of the venus that are loaded in the list.
//					// show the name, the category and the city
//					listTitle.add(i, venuesList.get(i).getName() + "\nOpen Now: " + venuesList.get(i).getOpenNow() + "\n(" + venuesList.get(i).getCategory() + ")");
//				}
				for (int i = 0; i < venuesList.size(); i++) {
					// make a list of the venus that are loaded in the list.
					// show the name, the category and the city
					listTitle.add(i, "lat"+latlist.get(i) + "\nlng : " + lnglist.get(i));
				}

				// set the results to the list
				// and show them in the xml
				myAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.row_layout, R.id.listText, listTitle);
				setListAdapter(myAdapter);
			}
		}
	}

	public static String makeCall(String url) {

		// string buffers the url
		StringBuffer buffer_string = new StringBuffer(url);
		String replyString = "";

		// instanciate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// instanciate an HttpGet
		HttpGet httpget = new HttpGet(buffer_string.toString());

		try {
			// get the responce of the httpclient execution of the url
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();

			// buffer input stream the result
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			// the result as a string is ready for parsing
			replyString = new String(baf.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(replyString);

		// trim the whitespaces
		return replyString.trim();
	}

	private static ArrayList<GooglePlace> parseGoogleParse(final String response) {

		ArrayList<GooglePlace> temp = new ArrayList<GooglePlace>();
		try {

			// make an jsonObject in order to parse the response
			JSONObject jsonObject = new JSONObject(response);

			// make an jsonObject in order to parse the response
			if (jsonObject.has("results")) {

				JSONArray jsonArray = jsonObject.getJSONArray("results");

				for (int i = 0; i < jsonArray.length(); i++) {
					GooglePlace poi = new GooglePlace();
					
					JSONObject geoobj=jsonArray.getJSONObject(i);
					if (geoobj.has("geometry")) {
						
						System.out.println("having geometry");
						txt.setText("having geometry");
						JSONObject locobj=geoobj.getJSONObject("geometry");
						if(locobj.has("location"))
						{
							txt.setText("having location");
							JSONObject latobj=locobj.getJSONObject("location");
							String lat=latobj.optString("lat");
							latlist.add(lat);
							txt.setText("lat"+lat);
							String lng=latobj.optString("lng");
							lnglist.add(lng);
							txt.setText("lng"+lng);
						}
						else
						{
							txt.setText("not having location");
						}
//						JSONObject geoobj=jsonArray.getJSONObject(i);
//						JSONObject locobj=geoobj.getJSONObject("location");
//						String lat=locobj.optString("lat");
//						String lng=locobj.optString("lng");
//						System.out.println("lat"+lat+"lng"+lng);
					}
					else
					{
						System.out.println("not having geometry");
						txt.setText("not having geometry");
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					if (jsonArray.getJSONObject(i).has("name")) {
						poi.setName(jsonArray.getJSONObject(i).optString("name"));
						poi.setRating(jsonArray.getJSONObject(i).optString("rating", " "));

						if (jsonArray.getJSONObject(i).has("opening_hours")) {
							if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {
								if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
									poi.setOpenNow("YES");
								} else {
									poi.setOpenNow("NO");
								}
							}
						} else {
							poi.setOpenNow("Not Known");
						}
						if (jsonArray.getJSONObject(i).has("types")) {
							JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");

							for (int j = 0; j < typesArray.length(); j++) {
								poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());
							}
						}
					}
					temp.add(poi);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<GooglePlace>();
		}
		return temp;

	}
}
