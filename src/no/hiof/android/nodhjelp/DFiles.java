package no.hiof.android.nodhjelp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;
import android.util.Log;


		
public class DFiles extends AsyncTask<String, Void, String>{
	HospitalMap hosMap;	
	
		public DFiles(HospitalMap hosMap) {
			this.hosMap = hosMap;
		}
		
		
		@Override
		protected String doInBackground(String... urls) {
			String webP = "", data="";
			try {
				URL url = new URL(urls[0]);
				HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				
				InputStream is = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				
				while ((data = reader.readLine()) != null ){
					webP += data + "\n";
				}
				
			} catch (Exception e) {
				Log.d("Feil", e.getMessage());
			}
			
			webP = webP.substring(20);
			
			String[] web = webP.split("\\},\\{");
			
			return web[0]+"}";
			
			
		}
		
		@Override
		protected void onPostExecute(String result){
			hosMap.Reader(result);
			
		}
		
}
