package com.example.newconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Customization extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue itemsQueue;
    final ArrayList<String> itemsCurrency = new ArrayList<>();
    Button currency11;
    Button currency12;
    Button currency21;
    Button currency22;
    Button currency31;
    Button currency32;
    Button currency41;
    Button currency42;
    Button currency51;
    Button currency52;
    Button currency61;
    Button currency62;
    Button save;
    Switch switch1;
    Switch switch2;
    Switch switch3;
    Switch switch4;
    Switch switch5;
    Switch switch6;
    TextView textView;
    SpinnerDialog spinnerCurrency;
    DBHelper dbHelper;
    String currency_to;
    String currency;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
        textView = (TextView) findViewById(R.id.textView);
        currency11=(Button) findViewById(R.id.currency11);
        currency12=(Button) findViewById(R.id.currency12);
        currency21=(Button) findViewById(R.id.currency21);
        currency22=(Button) findViewById(R.id.currency22);
        currency31=(Button) findViewById(R.id.currency31);
        currency32=(Button) findViewById(R.id.currency32);
        currency41=(Button) findViewById(R.id.currency41);
        currency42=(Button) findViewById(R.id.currency42);
        currency51=(Button) findViewById(R.id.currency51);
        currency52=(Button) findViewById(R.id.currency52);
        currency61=(Button) findViewById(R.id.currency61);
        currency62=(Button) findViewById(R.id.currency62);
        switch1=(Switch) findViewById(R.id.switch1);
        switch2=(Switch) findViewById(R.id.switch2);
        switch3=(Switch) findViewById(R.id.switch3);
        switch4=(Switch) findViewById(R.id.switch4);
        switch5=(Switch) findViewById(R.id.switch5);
        switch6=(Switch) findViewById(R.id.switch6);
        save = (Button) findViewById(R.id.save);

        currency11.setOnClickListener(this);
        currency12.setOnClickListener(this);
        currency21.setOnClickListener(this);
        currency22.setOnClickListener(this);
        currency31.setOnClickListener(this);
        currency32.setOnClickListener(this);
        currency41.setOnClickListener(this);
        currency42.setOnClickListener(this);
        currency51.setOnClickListener(this);
        currency52.setOnClickListener(this);
        currency61.setOnClickListener(this);
        currency62.setOnClickListener(this);
        save.setOnClickListener(this);

        itemsQueue = Volley.newRequestQueue(this);
        emtyTtemsCurrency(itemsCurrency);

        spinnerCurrency = new SpinnerDialog(Customization.this,itemsCurrency,"Select Currency");
        spinnerCurrency.setCancellable(true);
        spinnerCurrency.setShowKeyboard(false);
        dbHelper = new DBHelper(this);
        Log.d("Pars", "база");
        LoadOption();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    public void onClick (View view){
        switch (view.getId()){
            case R.id.currency11:
                itemsCurrencySpinner(currency11);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency12:
                itemsCurrencySpinner(currency12);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency21:
                itemsCurrencySpinner(currency21);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency22:
                itemsCurrencySpinner(currency22);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency31:
                itemsCurrencySpinner(currency31);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency32:
                itemsCurrencySpinner(currency32);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency41:
                itemsCurrencySpinner(currency41);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency42:
                itemsCurrencySpinner(currency42);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency51:
                itemsCurrencySpinner(currency51);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency52:
                itemsCurrencySpinner(currency52);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency61:
                itemsCurrencySpinner(currency61);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.currency62:
                itemsCurrencySpinner(currency62);
                spinnerCurrency.showSpinerDialog();
                break;
            case R.id.save:
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                database.delete(DBHelper.TABLE_CURRENCY,null,null);
                Log.d("Pars", "база");
                DBsrw(currency11,currency12,switch1);
                DBsrw(currency21,currency22,switch2);
                DBsrw(currency31,currency32,switch3);
                DBsrw(currency41,currency42,switch4);
                DBsrw(currency51,currency52,switch5);
                DBsrw(currency61,currency62,switch6);
                Log.d("Pars", "база");
                SvaeOption();
                Intent intent = new Intent(Customization.this, MainActivity.class);
                startActivity(intent);
                break;


        }


    }



    private void DBsrw(Button to, Button in, Switch on_of){
            Boolean Check = on_of.isChecked();
            String c_to;
            String c_in;
            String c;
            c = "currency";
            c_to =(String) to.getText();
            c_in =(String) in.getText();
            if(Check == true && c_in != c_to && c_to != c) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                currency_to = to.getText().toString();
                currency = in.getText().toString();
                contentValues.put(DBHelper.KEY_CURRENCY_TO, currency_to);
                contentValues.put(DBHelper.KEY_CURRENCY, currency);

                database.insert(DBHelper.TABLE_CURRENCY, null, contentValues);
                Log.d("Pars", "база");
                Cursor cursor = database.query(DBHelper.TABLE_CURRENCY, null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    Log.d("Pars", "база4");
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_TO);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY);
                    do {
                        Log.d("Pars", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();
            }

    }

    private void emtyTtemsCurrency(final ArrayList<String> items) {
        String url = "http://apilayer.net/api/live?access_key=9c169bf4c6824b5f66efe75011933c85";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONObject last = (JSONObject) response.get("quotes");
                    Iterator<String> keys = last.keys();
                    do {
                        String k = keys.next().toString();
                        k = k.substring(3, k.length());
                        items.add(k);
                    } while (keys.hasNext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        itemsQueue.add(request);
    }

    public void itemsCurrencySpinner(final Button a){

        spinnerCurrency.bindOnSpinerListener(new OnSpinerItemClick() {

            public void onClick(String item, int position) {
                Toast.makeText(Customization.this, item,Toast.LENGTH_SHORT).show();
                a.setText(item);
            }

        });

    }

    private void SvaeOption() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("currency11", currency11.getText().toString());
        ed.putString("currency12", currency12.getText().toString());
        ed.putString("currency21", currency21.getText().toString());
        ed.putString("currency22", currency22.getText().toString());
        ed.putString("currency31", currency31.getText().toString());
        ed.putString("currency32", currency32.getText().toString());
        ed.putString("currency41", currency41.getText().toString());
        ed.putString("currency42", currency42.getText().toString());
        ed.putString("currency51", currency51.getText().toString());
        ed.putString("currency52", currency52.getText().toString());
        ed.putString("currency61", currency61.getText().toString());
        ed.putString("currency62", currency62.getText().toString());
        ed.putBoolean("switch1", switch1.isChecked());
        ed.putBoolean("switch2", switch2.isChecked());
        ed.putBoolean("switch3", switch3.isChecked());
        ed.putBoolean("switch4", switch4.isChecked());
        ed.putBoolean("switch5", switch5.isChecked());
        ed.putBoolean("switch6", switch6.isChecked());
        ed.apply();
    }
    private void LoadOption(){
        sPref = getPreferences(MODE_PRIVATE);
        currency11.setText(sPref.getString("currency11",""));
        currency12.setText(sPref.getString("currency12",""));
        currency21.setText(sPref.getString("currency21",""));
        currency22.setText(sPref.getString("currency22",""));
        currency31.setText(sPref.getString("currency31",""));
        currency32.setText(sPref.getString("currency32",""));
        currency41.setText(sPref.getString("currency41",""));
        currency42.setText(sPref.getString("currency42",""));
        currency51.setText(sPref.getString("currency51",""));
        currency52.setText(sPref.getString("currency52",""));
        currency61.setText(sPref.getString("currency61",""));
        currency62.setText(sPref.getString("currency62",""));
        switch1.setChecked(sPref.getBoolean("switch1",false));
        switch2.setChecked(sPref.getBoolean("switch2",false));
        switch3.setChecked(sPref.getBoolean("switch3",false));
        switch4.setChecked(sPref.getBoolean("switch4",false));
        switch5.setChecked(sPref.getBoolean("switch5",false));
        switch6.setChecked(sPref.getBoolean("switch6",false));

    }

}