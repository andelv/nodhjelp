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
			
			text.setText("1. Sjekk om pasienten reagerer på tilrop og forsiktig risting.\n\n"
					+ "2. Hvis pasienten ikke reagerer: Tilkall hjelp.\n\n"
					+ "3. Snu pasienten på ryggen og åpne luftveiene (bøy hodet bakover, og løft haka fram). Sjekk om pasienten puster normalt (se, lytt og føl etter normal pust i inntil 10 sekunder). Legg pasienten i sideleie bare hvis pusten fortsatt er normal etter ett minutt. Fortsett nøye observasjon av pusten.\n\n"
					+ "4. Hvis pasienten ikke puster normalt eller slutter å puste normalt: Ring 113 og skaff hjelp. Hvis mulig, få noen andre til å ringe 113 og å hente en hjertestarter (hvis tilgjengelig).\n\n"
					+ "5. Start HLR med 30 brystkompresjoner fulgt av to innblåsninger (30:2). Brystkompresjonene bør være 5-6 cm dype med en takt på 100 i minuttet. Hver innblåsning skal ta ca. ett sekund og avsluttes straks brystkassen hever seg.\n\n"
					+ "6. Fortsett til hjelpen kommer."
					+ "\nkilde: http://snl.no/førstehjelp\n\n\n\n");

		} else if (selected.equals("Blødninger")) {
			
			text.setText("Hos personer som puster og har puls ser man etter tegn til ytre blødninger eller brudd. Større blødninger forsøkes om mulig stoppet med stramme bandasjer eller direkte trykk over det blødende stedet. Avklemming (trykk med fingre/hånd) av pulsåren nærmere hjertet enn det blødende stedet kan også være en nødløsning. Indre blødninger kan ikke behandles i førstehjelpssituasjoner.\nkilde: http://snl.no/førstehjelp");

		} else if (selected.equals("Førstehjelpsskrin, sjekkliste")) {
			
			text.setText("1 Kompresser\n2 Enkeltmannspakker\n3 Trekanttørkle\n4 Nettingbandasje eller gassbind \n5 Plaster\n6 Øyevann med øyeglass\n7 Rensekluter\n8 Saks \n9 Pinsett\n10 Sår - rensemiddel\n11 Elastiske bandasjer \n12 Engangshansker\n13 Sikkerhetsnåler\n14 Smertestillende tabletter\n15 Sårsalve\n16 Ansiktsmaske eller ansiktsduk\n17 Ispose\n18 «Burn dressing»\n19 Bandasjeteip – smal - 2 ruller\n20 Bandasjeteip – bred – 1 rull\n21 Jodd\n\nKilde:http://bokasnettressurs.no/asset/168/1/168_1.pdf");
		} else if (selected.equals("Rødekors (ekstern link)")) {

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
