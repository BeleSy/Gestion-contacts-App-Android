package com.example.exemple_sqlite_3;

import java.util.ArrayList;
import java.util.List;

import com.example.exemple_sqlite_3.R;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class EmployeeDetails extends ListActivity {

    protected TextView employeeName;
    protected TextView employeeTitle;
    protected TextView officePhone;
    protected TextView cellPhone;
    protected TextView email;
    ImageButton btModifier, btEffacer;
    protected int contactId;
    //protected int managerId;
    protected SQLiteDatabase db;
    protected List<EmployeeAction> actions;
    protected EmployeeActionAdapter adapter;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details);

        contactId = getIntent().getIntExtra("CONTACT_ID", 0);
        db = (new DatabaseHelper(this)).getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM contacts as emp WHERE emp._id = ?",
                new String[]{""+contactId});

        if (cursor.getCount() == 1)
        {
            cursor.moveToFirst();

            employeeName = (TextView) findViewById(R.id.employeeName);
            employeeName.setText(cursor.getString(cursor.getColumnIndex("firstName")) + " " + cursor.getString(cursor.getColumnIndex("lastName")));
/*
            employeeTitle = (TextView) findViewById(R.id.title);
            employeeTitle.setText(cursor.getString(cursor.getColumnIndex("telephone")));
*/
            actions = new ArrayList<EmployeeAction>();

            String officePhone = cursor.getString(cursor.getColumnIndex("telephone"));
            if (officePhone != null) {
                actions.add(new EmployeeAction("Téléphone", officePhone, EmployeeAction.ACTION_CALL));
            }
/*
            String cellPhone = cursor.getString(cursor.getColumnIndex("cellPhone"));
            if (cellPhone != null) {
                actions.add(new EmployeeAction("Call mobile", cellPhone, EmployeeAction.ACTION_CALL));
                actions.add(new EmployeeAction("SMS", cellPhone, EmployeeAction.ACTION_SMS));
            }

            String email = cursor.getString(cursor.getColumnIndex("email"));
            if (email != null) {
                actions.add(new EmployeeAction("Email", email, EmployeeAction.ACTION_EMAIL));
            }

            managerId = cursor.getInt(cursor.getColumnIndex("managerId"));
            if (managerId>0) {
                actions.add(new EmployeeAction("View manager", cursor.getString(cursor.getColumnIndex("firstName")) + " " + cursor.getString(cursor.getColumnIndex("lastName")), EmployeeAction.ACTION_VIEW));
            }
*/
            adapter = new EmployeeActionAdapter();
            setListAdapter(adapter);

        }

        btModifier = (ImageButton) findViewById(R.id.btChangerNumTel);
        btEffacer = (ImageButton) findViewById(R.id.btEffContact);

        btEffacer.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //String sql = "DELETE FROM contacts WHERE id="+ contactId;
                //db.execSQL(sql);
                       //cursor.getInt(cursor.getString(cursor.getColumnIndex("_id INTEGER PRIMARY KEY AUTOINCREMENT")));

                db.execSQL("delete from contacts where _id='"+contactId+"'");

              //  db.delete(contacts, String whereClause, String[] whereArgs);

                //Toast.makeText(EmployeeDetails.this,"Contact effacer" + "//contactid" + contactId,Toast.LENGTH_LONG).show();
                Intent intentR = new Intent(EmployeeDetails.this, EmployeeList.class);
                intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentR);
                EmployeeDetails.this.finish();
            }
        });

        btModifier.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Création du dialogue
                final Dialog dialog = new Dialog(EmployeeDetails.this);
                // View du dialogue
                dialog.setContentView(R.layout.modifier_telephone);
                dialog.setTitle("Modifier le numéro");
                // Contenu

                Button btnLogin = (Button) dialog.findViewById(R.id.btnEnregistrerNumero);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelModifNum);

                final EditText telephone = (EditText)dialog.findViewById(R.id.txtNouveauNumero);

                // Écouteurs
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(telephone.getText().toString().trim().length() > 0){
                            db.execSQL("update contacts set telephone='" + telephone.getText().toString() + "' where _id='" + contactId + "'");

                            //Validation ici
                            //Toast.makeText(EmployeeDetails.this,"Modification complété// tél:"+ telephone.getText().toString(), Toast.LENGTH_LONG).show();

                            //Retour à l'activité
                            dialog.dismiss();
                            finish();
                            /*

                           Intent intentR = new Intent(EmployeeDetails.this, EmployeeDetails.class);
                           intentR.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(intentR);
                           EmployeeDetails.this.finish();

                            */
                            Intent intent = new Intent(EmployeeDetails.this, EmployeeDetails.class);
                            //Cursor cursor = (Cursor) adapter.getItem(contactId);
                            intent.putExtra("CONTACT_ID", contactId);
                            //Toast.makeText(EmployeeList.this,""+cursor.getInt(cursor.getColumnIndex("_id")),Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(EmployeeDetails.this,"Vous devez remplir tous les champs", Toast.LENGTH_LONG).show();
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
            }

        });
    }


    public void onListItemClick(ListView parent, View view, int position, long id) {

        EmployeeAction action = actions.get(position);

        Intent intent;
        switch (action.getType()) {

            case EmployeeAction.ACTION_CALL:
                Uri callUri = Uri.parse("tel:" + action.getData());
                intent = new Intent(Intent.ACTION_CALL, callUri);
                startActivity(intent);
                break;
/*
            case EmployeeAction.ACTION_EMAIL:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{action.getData()});
                startActivity(intent);
                break;

            case EmployeeAction.ACTION_SMS:
                Uri smsUri = Uri.parse("sms:" + action.getData());
                intent = new Intent(Intent.ACTION_VIEW, smsUri);
                startActivity(intent);
                break;

            case EmployeeAction.ACTION_VIEW:
                intent = new Intent(this, EmployeeDetails.class);
                intent.putExtra("EMPLOYEE_ID", managerId);
                startActivity(intent);
                break;
                */
        }
    }

    class EmployeeActionAdapter extends ArrayAdapter<EmployeeAction> {

        EmployeeActionAdapter() {
            super(EmployeeDetails.this, R.layout.action_list_item, actions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            EmployeeAction action = actions.get(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.action_list_item, parent, false);
            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText(action.getLabel());
            TextView data = (TextView) view.findViewById(R.id.data);
            data.setText(action.getData());
            return view;
        }
    }
}