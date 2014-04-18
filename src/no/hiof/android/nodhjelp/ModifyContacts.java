package no.hiof.android.nodhjelp;

import java.util.ArrayList;



import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.provider.ContactsContract;

public class ModifyContacts extends ActionBarActivity {


	String fastlegeTlf = ShowContacts.getFastlegeTlf();
	String iceTlf = ShowContacts.getIceTlf();
	String endretFastlegeTlf = null;
	String endretIceTlf = null;
	EditText tekstTlfFastlege = null;
	EditText tekstTlfICE = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_contacts);
		oppdaterFelt();	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void oppdaterFelt()
	{
		tekstTlfFastlege = (EditText)findViewById(R.id.editText1);
	    tekstTlfICE = (EditText)findViewById(R.id.editText3);
		
		tekstTlfFastlege.setText(fastlegeTlf);
		tekstTlfICE.setText(ShowContacts.iceTlf);	
	}  

	 private boolean createContact(String name, String phone) {
	    	ContentResolver cr = getContentResolver();
	    	
	    	Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
	                null, null, null, null);
	        
	    	if (cur.getCount() > 0) {
	        	while (cur.moveToNext()) {
	        		String existName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	        		if (existName.contains(name)) {
	                	Toast.makeText(ModifyContacts.this,"The contact name: " + name + " already exists", Toast.LENGTH_SHORT).show();
	                	
	                	return false;        			
	        		}
	        	}
	    	}
	    	
	        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
	                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "accountname@gmail.com")
	                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
	                .build());
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                .withValue(ContactsContract.Data.MIMETYPE,
	                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
	                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
	                .build());
	        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	                .withValue(ContactsContract.Data.MIMETYPE,
	                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
	                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
	                .build());

	        
	        try {
				cr.applyBatch(ContactsContract.AUTHORITY, ops);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	Toast.makeText(ModifyContacts.this, "Created a new contact with name: " + name + " and Phone No: " + phone, Toast.LENGTH_SHORT).show();
	    	return true;
	    }
	 
	   private void updateContact(String name, String phone) {
	    	ContentResolver cr = getContentResolver();
	 
	        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " + 
	        			ContactsContract.Data.MIMETYPE + " = ? AND " +
	        			String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? ";
	        String[] params = new String[] {name,
	        		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
	        		String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};

	        Cursor phoneCur = managedQuery(ContactsContract.Data.CONTENT_URI, null, where, params, null);
	        
	        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	        
	        if ( (null == phoneCur)  ) {
	        	createContact(name, phone);
	        } else {
	        	ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
	        	        .withSelection(where, params)
	        	        .withValue(ContactsContract.CommonDataKinds.Phone.DATA, phone)
	        	        .build());
	        }
	        
	        phoneCur.close();
	        
	        try {
				cr.applyBatch(ContactsContract.AUTHORITY, ops);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	public void startLagreKontakt(View view)
	{
		endretFastlegeTlf  =  tekstTlfFastlege.getText().toString();
		endretIceTlf = tekstTlfICE.getText().toString();
		
		if (!endretFastlegeTlf.equals(fastlegeTlf))
		{
			if(createContact("Fastlege", endretFastlegeTlf)==false)
			{
				updateContact("Fastlege", endretFastlegeTlf);
			}
		}
		
		if (!endretIceTlf.equals(iceTlf))
		{
			if(createContact("ICE", endretIceTlf)==false)
			   {
				   updateContact("ICE", endretIceTlf);
			   }
		}
	 
		
		Intent startVisKontakt = new Intent(this, ShowContacts.class);
		startActivity(startVisKontakt);
	}
	
	public void startAvbryt(View view)
	{
		Intent startVisKontakt = new Intent(this, ShowContacts.class);
		startActivity(startVisKontakt);
	}


	


}
