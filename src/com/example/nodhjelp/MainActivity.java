package com.example.nodhjelp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			alertMessage();
			return true;
			
		case R.id.get_gps_pos:
			Intent intent = new Intent(this, PositionActivity.class);
			startActivity(intent);
			
			
			
		default:
			return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void alertMessage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Ringe 113?");
		builder.setMessage("Når du snakker med 113, si da:\n" +
				"'Jeg oppgir nå grader og desimalminutter'\n\n" +
				"Er du sikker på at du ønsker å ringe nødnummer: 113?");
		
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				makeCall("113");
			}
		});
		
		builder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		builder.show();
	}
	
	private void makeCall(String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+phone));
		startActivity(intent);
	}
}
