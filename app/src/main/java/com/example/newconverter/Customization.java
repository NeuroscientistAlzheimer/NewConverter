package com.example.newconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

        itemsQueue = Volley.newRequestQueue(this);
        emtyTtemsCurrency(itemsCurrency);

        spinnerCurrency = new SpinnerDialog(Customization.this,itemsCurrency,"Select Currency");
        spinnerCurrency.setCancellable(true);
        spinnerCurrency.setShowKeyboard(false);
        dbHelper = new DBHelper(this);

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
                DBsrw(currency11,currency12,switch1);
                DBsrw(currency21,currency22,switch2);
                DBsrw(currency31,currency32,switch3);
                DBsrw(currency41,currency42,switch4);
                DBsrw(currency51,currency52,switch5);
                DBsrw(currency61,currency62,switch6);
                break;


        }


    }
    private void DBsrw(Button to, Button in, Switch on_of){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        currency_to = to.getText().toString();
        currency = in.getText().toString();
        contentValues.put(DBHelper.KEY_CURRENCY_TO, currency_to);
        contentValues.put(DBHelper.KEY_CURRENCY, currency);

        database.insert(DBHelper.TABLE_CURRENCY, null, contentValues);
    }

    private void emtyTtemsCurrency(final ArrayList<String> items) {
        String url = "http://apilayer.net/api/live?access_key=ad16a4ab373cbeca1bdbf0fd5b1e77ed";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Pars", "1присвоение");
                    JSONObject last = (JSONObject) response.get("quotes");
                    Log.d("Pars", "2присвоение");
                    Iterator<String> keys = last.keys();
                    do {
                        String k = keys.next().toString();
                        k = k.substring(3, k.length());
                        items.add(k);
                        Log.d("Pars", "3завершение" + k);
                    } while (keys.hasNext());
                    Log.d("Pars", "3завершение" + keys);
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

}