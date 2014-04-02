package no.hiof.android.nodhjelp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class UtilHospitalCall {
	public static void alertMessage(String hsName, final String hsPhone, final Context context) { 
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Ringe legevakt?");
		builder.setMessage("Ønsker du å ringe" + hsName + "?:  Om det er " +
				"en nødsituasjon ring 113. Er du i tvil ring 113!");
		
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				makeCall(hsPhone, context);
				String toastText = "Ringer "+hsPhone;
				Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		builder.setNegativeButton("Nei, ring 113", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				makeCall("113", context);
				
			}
		});
		
		builder.show();
	}
	
	private static void makeCall(String phone, Context context) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+phone));
		context.startActivity(intent);
	}
	
}
