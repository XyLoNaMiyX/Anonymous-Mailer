package lowe.lonamiwebs.anonymousmailer;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	WebView wvMailer;
	
	String urlPage;
	String sTo;
	String scTo;
	String sFrom;
	String sSubject;
	String sMessage;
	String sFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Spinner spinner = (Spinner) findViewById(R.id.sFont);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.fonts, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapter);

		wvMailer = (WebView) findViewById(R.id.wvMailer);
		
		urlPage = "http://enviaremailanonimogratis.lonamiwebs.github.io/usingapp.php?";
		sTo = "to=";
		sFrom = "&from=";
		sSubject = "&subject=";
		sMessage = "&message=";
		sFont = "&pickedFont=";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void button_send(View v) throws Exception
	{
		EditText tTo = (EditText) findViewById(R.id.etTo);
		EditText tFrom = (EditText) findViewById(R.id.etFrom);
		EditText tSubject = (EditText) findViewById(R.id.etSubject);
		EditText tContent = (EditText) findViewById(R.id.etContent);
		Spinner tFont = (Spinner) findViewById(R.id.sFont);
		String scTo = tTo.getText().toString();
		String scFrom = tFrom.getText().toString();
		String scSubject = tSubject.getText().toString();
		String scContent = tContent.getText().toString();
		String scFont = tFont.getSelectedItem().toString();

		scSubject = URLEncoder.encode(scSubject, "ISO-8859-1");
		scContent = URLEncoder.encode(scContent, "ISO-8859-1");
		
		String doneUrl = urlPage + sTo + scTo + sFrom + scFrom + sSubject + scSubject + sMessage + scContent + sFont + scFont;
		sendMail(doneUrl);
	}
	
	public void sendMail(String url)
	{	
		wvMailer.loadUrl(url);
		
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		boolean isConnected = false;
		State cMobi = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		State cWifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		
		if (cMobi == NetworkInfo.State.CONNECTED || cMobi == NetworkInfo.State.CONNECTING)
			isConnected = true;
		
		if (cWifi == NetworkInfo.State.CONNECTED || cWifi == NetworkInfo.State.CONNECTING)
			isConnected = true;
		
		if (isConnected)
			Toast.makeText(getApplicationContext(), getString(R.string.success) + " " + scTo + "!" , Toast.LENGTH_LONG).show();
		else
			Toast.makeText(getApplicationContext(), getString(R.string.nosuccess), Toast.LENGTH_LONG).show();
			
	}

}
