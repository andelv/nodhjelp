package no.hiof.android.nodhjelp;



import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.ContactsContract;

public class ShowContacts extends ActionBarActivity {
	
	public TextView outputText;
    final static String NO_DATA = "";
	static String fastlegeTlf = NO_DATA;

	static String iceTlf = NO_DATA;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_contacts);
		outputText = (TextView) findViewById(R.id.textView1);
		outputText.setText("\nFastlege\n\t\tTelefonnr: "+"\n\nICE\n\t\tTelefonnr: ");
		fetchContacts();

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			
		case R.id.get_hospital_map:
			Intent intenHos = new Intent(this, HospitalMap.class);
			startActivity(intenHos);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
			
		}
	}
	public void startModifyContacts(View view)
	{
		Intent startModifyContacts = new Intent(this, ModifyContacts.class);
		startActivity(startModifyContacts);
	}

	public void callDoctor(View view) {
		
		String phoneCallUri = "tel:"+fastlegeTlf;
	     Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
	     phoneCallIntent.setData(Uri.parse(phoneCallUri));
	     startActivity(phoneCallIntent);
	}
	
	public void callIce(View view) {
		
		String phoneCallUri = "tel:"+iceTlf;
	     Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
	     phoneCallIntent.setData(Uri.parse(phoneCallUri));
	     startActivity(phoneCallIntent);
	}
  
	public void fetchContacts() {

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		StringBuffer output = new StringBuffer();

		ContentResolver contentResolver = getContentResolver();

		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				if (name.equalsIgnoreCase("Fastlege"))
				{
					int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
					
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
				
					while (phoneCursor.moveToNext()) 
					{
						fastlegeTlf = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
					}
					
					phoneCursor.close();
				}	
				
				else if(name.equalsIgnoreCase("ICE"))
				{
					int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
					
			        // Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

					while (phoneCursor.moveToNext()) 
					{
						iceTlf = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
					}

					phoneCursor.close();	
				}	
			}

			outputText.setText("\nFastlege\n\t\tTelefonnr: "+ fastlegeTlf + "\n\nICE\n\t\tTelefonnr: "+ iceTlf);
			
		}

	}

	public static String getFastlegeTlf()
	{
		if(fastlegeTlf.equals(NO_DATA))
		{
			return "";
		}
		else
		{
			return fastlegeTlf;  
		}
			
	}

	public static String getIceTlf()
	{
		if(iceTlf.equals(NO_DATA))
		{
			return "";
		}
		else
		{
			return iceTlf;
		}
		
	}



	

}
