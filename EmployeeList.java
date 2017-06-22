package com.example.exemple_sqlite_3;

import com.example.exemple_sqlite_3.R;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmployeeList extends ListActivity {
	ActionBar bar=null;
	protected EditText searchText;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected ListAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = (new DatabaseHelper(this)).getWritableDatabase();
        searchText = (EditText) findViewById (R.id.searchText);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_employee_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_ajouter:
				// Création du dialogue
				final Dialog dialog = new Dialog(this);
				// View du dialogue
				dialog.setContentView(R.layout.layout_ajouter_contact);
				dialog.setTitle("Ajouter un contact");
				// Contenu

				Button btnLogin = (Button) dialog.findViewById(R.id.btnEnregistrer);
				Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

				final EditText nom = (EditText)dialog.findViewById(R.id.txtNom);
				final EditText prenom = (EditText)dialog.findViewById(R.id.txtPrenom);
				final EditText telephone = (EditText)dialog.findViewById(R.id.txtTelephone);
				// Écouteurs
				btnLogin.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						if(nom.getText().toString().trim().length() > 0 && prenom.getText().toString().trim().length() > 0 && telephone.getText().toString().trim().length() > 0)
						{



							/*
							String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
									"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
									"firstName TEXT, " +
									"lastName TEXT, " +
									"telephone TEXT)";
							db.execSQL(sql);

							ContentValues values = new ContentValues();

							values.put("firstName", prenom.getText().toString());
							values.put("lastName", nom.getText().toString());
							values.put("telephone", telephone.getText().toString());
							db.insert("contacts", "lastName", values);
							*/

							//Validation ici
							Toast.makeText(EmployeeList.this,"Enregistrement complété", Toast.LENGTH_LONG).show();

							//Retour à l'activité
							dialog.dismiss();
						}
						else
						{
							Toast.makeText(EmployeeList.this,"Vous devez remplir tous les champs", Toast.LENGTH_LONG).show();
						}
					}
				});
				btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// Rendre dialog visible.
				dialog.show();

				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

    @SuppressWarnings("deprecation")
	public void search(View view) {
    	// || is the concatenation operation in SQLite
		cursor = db.rawQuery("SELECT _id, firstName, lastName, telephone FROM contacts WHERE firstName || ' ' || lastName LIKE ?",
						new String[]{"%" + searchText.getText().toString() + "%"});

		//-------------------------------------------------------------------------------------------------
		/*
		//partie décoder info
		String result = null;
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://127.0.0.1:8080/getAllContacts.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.e("log_tag", "connection success");
			Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection" + e.toString());
			Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();
		}

		//convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso - 8859 - 1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				//  Toast.makeText(getApplicationContext(), “Input Reading pass”, Toast.LENGTH_SHORT).show();
			}
			is.close();
			result = sb.toString();
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result" + e.toString());
			Toast.makeText(getApplicationContext(), "Input reading fail", Toast.LENGTH_SHORT).show();
		}
*/


		//-------------------------------------------------------------------------------------------------

		adapter = new SimpleCursorAdapter(
				this, 
				R.layout.employee_list_item, 
				cursor, 
				new String[] {"firstName", "lastName", "telephone"},
				new int[] {R.id.firstName, R.id.lastName, R.id.title});
		setListAdapter(adapter);
    }
    
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	Intent intent = new Intent(this, EmployeeDetails.class);
    	Cursor cursor = (Cursor) adapter.getItem(position);
    	intent.putExtra("CONTACT_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	//Toast.makeText(EmployeeList.this,""+cursor.getInt(cursor.getColumnIndex("_id")),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


}