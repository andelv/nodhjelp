package no.hiof.android.nodhjelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FirstAidInstructions extends ActionBarActivity implements
		OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_aid_instructions);

		Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.informationSelection,
				android.R.layout.simple_spinner_item);
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

	public void getInformation(View view) {
		Spinner spinner = (Spinner) findViewById(R.id.infoSpinner);
		TextView text = (TextView) findViewById(R.id.textView1);

		String selected = spinner.getSelectedItem().toString();

		if (selected.equals("Hjerte-Lunge-Redning")) {
			
			text.setText("1. Sjekk om pasienten reagerer p� tilrop og forsiktig risting.\n\n"
					+ "2. Hvis pasienten ikke reagerer: Tilkall hjelp.\n\n"
					+ "3. Snu pasienten p� ryggen og �pne luftveiene (b�y hodet bakover, og l�ft haka fram). Sjekk om pasienten puster normalt (se, lytt og f�l etter normal pust i inntil 10 sekunder). Legg pasienten i sideleie bare hvis pusten fortsatt er normal etter ett minutt. Fortsett n�ye observasjon av pusten.\n\n"
					+ "4. Hvis pasienten ikke puster normalt eller slutter � puste normalt: Ring 113 og skaff hjelp. Hvis mulig, f� noen andre til � ringe 113 og � hente en hjertestarter (hvis tilgjengelig).\n\n"
					+ "5. Start HLR med 30 brystkompresjoner fulgt av to innbl�sninger (30:2). Brystkompresjonene b�r v�re 5-6 cm dype med en takt p� 100 i minuttet. Hver innbl�sning skal ta ca. ett sekund og avsluttes straks brystkassen hever seg.\n\n"
					+ "6. Fortsett til hjelpen kommer."
					+ "\nkilde: http://snl.no/f�rstehjelp\n\n\n\n");

		} else if (selected.equals("Bl�dninger")) {
			
			text.setText("Hos personer som puster og har puls ser man etter tegn til ytre bl�dninger eller brudd. St�rre bl�dninger fors�kes om mulig stoppet med stramme bandasjer eller direkte trykk over det bl�dende stedet. Avklemming (trykk med fingre/h�nd) av puls�ren n�rmere hjertet enn det bl�dende stedet kan ogs� v�re en n�dl�sning. Indre bl�dninger kan ikke behandles i f�rstehjelpssituasjoner.\nkilde: http://snl.no/f�rstehjelp");

		} else if (selected.equals("F�rstehjelpsskrin, sjekkliste")) {
			
			text.setText("1 Kompresser\n2 Enkeltmannspakker\n3 Trekantt�rkle\n4 Nettingbandasje eller gassbind \n5 Plaster\n6 �yevann med �yeglass\n7 Rensekluter\n8 Saks \n9 Pinsett\n10 S�r - rensemiddel\n11 Elastiske bandasjer \n12 Engangshansker\n13 Sikkerhetsn�ler\n14 Smertestillende tabletter\n15 S�rsalve\n16 Ansiktsmaske eller ansiktsduk\n17 Ispose\n18 �Burn dressing�\n19 Bandasjeteip � smal - 2 ruller\n20 Bandasjeteip � bred � 1 rull\n21 Jodd\n\nKilde:http://bokasnettressurs.no/asset/168/1/168_1.pdf");
		} else if (selected.equals("R�dekors (ekstern link)")) {

			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.rodekors.no"));
			startActivity(browserIntent);
		} else if (selected.equals("Lommelegen (ekstern link)")) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.lommelegen.no"));
			startActivity(browserIntent);
		}

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
	
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

}
