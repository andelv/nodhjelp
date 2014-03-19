package no.hiof.android.nodhjelp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;
import android.util.Log;


		
public class DownloadFiles extends AsyncTask<String, Void, String>{
	PositionActivity posAct;	
	
		public DownloadFiles(PositionActivity posAct) {
			this.posAct = posAct;
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
			
			webP = webP.substring(35);
			
			String[] web = webP.split("\\},\\{");
			
			return web[0]+"}";
			
			
		}
		
		@Override
		protected void onPostExecute(String result){
			posAct.Reader(result);
			
		}
		
}
