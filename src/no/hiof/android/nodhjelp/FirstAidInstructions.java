package no.hiof.android.nodhjelp;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class FirstAidInstructions extends ActionBarActivity implements OnItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_aid_instructions);
		
		Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.informationSelection, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  public void onItemSelected(AdapterView<?> parent, View view, 
	            int pos, long id) {
	        // An item was selected. You can retrieve the selected item using (String)parent.getItemAtPosition(pos);
	
		  
		  
		  
	         
	    }
	 

	    public void onNothingSelected(AdapterView<?> parent) {
	        // Another interface callback
	    }
	    
	    
	    
	    

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()) {
		
		case R.id.call_emergency:
			UtilEMSCall.alertMessage(this);
			return true;
			
		case R.id.get_gps_pos:
			Intent intent = new Intent(this, PositionActivity.class);
			startActivity(intent);
			return true;
			
		case R.id.get_show_contacts:
			Intent intentContacts = new Intent(this, ShowContacts.class);
			startActivity(intentContacts);
			return true;
			
		case R.id.get_gps_history:
			Intent intentGPS = new Intent(this, GPSTracker.class);
			startActivity(intentGPS);
			return true;
			
		case R.id.get_show_instructions:
			Intent intentInstructions = new Intent(this, FirstAidInstructions.class);
			startActivity(intentInstructions);
			return true;
			
		case R.id.get_hospital_map:
			Intent intenHos = new Intent(this, HospitalMap.class);
			startActivity(intenHos);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
			
		}
	}

	
}
