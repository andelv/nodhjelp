package no.hiof.android.nodhjelp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class UtilEMSCall {

	
	
	public static void alertMessage(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Ringe 113?");
		builder.setMessage("Når du snakker med 113, si da:\n" +
				"'Jeg oppgir nå grader og desimalminutter'\n\n" +
				"Er du sikker på at du ønsker å ringe nødnummer: 113?");
		
		builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				makeCall("113", context);
				String toastText = "Ringer 113";
				Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
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
	
	private static void makeCall(String phone, Context context) {
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:"+phone));
		context.startActivity(intent);
	}
	
}
