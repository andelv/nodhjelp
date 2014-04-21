package no.hiof.android.nodhjelp;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
			Intent intentInstructions = new Intent(this,
					FirstAidInstructions.class);
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

	public void oppdaterFelt() {
		tekstTlfFastlege = (EditText) findViewById(R.id.editText1);
		tekstTlfICE = (EditText) findViewById(R.id.editText3);

		tekstTlfFastlege.setText(fastlegeTlf);
		tekstTlfICE.setText(ShowContacts.iceTlf);
	}

	private boolean createContact(String name, String phone) {
		ContentResolver cr = getContentResolver();

		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String existName = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (existName.contains(name)) {

					return false;
				}
			}
		}

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						"accountname@gmail.com")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"com.google").build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
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

		return true;
	}

	private void updateContact(String name, String phone) {
		ContentResolver cr = getContentResolver();

		String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND "
				+ ContactsContract.Data.MIMETYPE + " = ? AND "
				+ String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE)
				+ " = ? ";
		String[] params = new String[] {
				name,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
				String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME) };

		Cursor phoneCur = managedQuery(ContactsContract.Data.CONTENT_URI, null,
				where, params, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if ((null == phoneCur)) {
			createContact(name, phone);
		} else {
			ops.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(where, params)
					.withValue(ContactsContract.CommonDataKinds.Phone.DATA,
							phone).build());
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

	public void startLagreKontakt(View view) {
		endretFastlegeTlf = tekstTlfFastlege.getText().toString();
		endretIceTlf = tekstTlfICE.getText().toString();

		if (!endretFastlegeTlf.equals(fastlegeTlf)) {
			if (createContact("Fastlege", endretFastlegeTlf) == false) {
				updateContact("Fastlege", endretFastlegeTlf);
			}
		}

		if (!endretIceTlf.equals(iceTlf)) {
			if (createContact("ICE", endretIceTlf) == false) {
				updateContact("ICE", endretIceTlf);
			}
		}

		Intent startVisKontakt = new Intent(this, ShowContacts.class);
		startActivity(startVisKontakt);
	}

	public void startAvbryt(View view) {
		Intent startVisKontakt = new Intent(this, ShowContacts.class);
		startActivity(startVisKontakt);
	}

}
