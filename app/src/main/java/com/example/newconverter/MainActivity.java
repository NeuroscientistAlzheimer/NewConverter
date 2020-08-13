package com.example.newconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private RequestQueue itemsQueue;
    private TextView mTextViewResult;
    SpinnerDialog spinnerCurrency1;
    SpinnerDialog spinnerCurrency2;
    Button currency1;
    Button currency2;
    Button button_res;
    Button Customization_activity;
    Button swap;
    EditText num;
    TextView DBtextRes;
    TextView DBtextName;
    float numCurrensy1;
    float numCurrensy2;
    float calculationCurrensy;
    final ArrayList<String> itemsCurrency = new ArrayList<>();
    float number;
    DBHelper dbHelper;
    SharedPreferences sPref;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewResult = findViewById(R.id.text_view_result);
        mQueue = Volley.newRequestQueue(this);
        itemsQueue = Volley.newRequestQueue(this);
        Log.d("Pars","Начало");
        Log.d("Pars","Конец");
        emtyTtemsCurrency();
        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);
        swap = findViewById(R.id.swap);
        button_res = findViewById(R.id.button_res);
        Customization_activity = findViewById(R.id.Customization_activity);
        num = findViewById(R.id.num);
        DBtextRes = findViewById(R.id.DBTextRes);
        DBtextName = findViewById(R.id.DBTextName);

        spinnerCurrency1 = new SpinnerDialog(MainActivity.this,itemsCurrency,"Select Currency");
        spinnerCurrency1.setCancellable(true);
        spinnerCurrency1.setShowKeyboard(false);
        spinnerCurrency1.bindOnSpinerListener(new OnSpinerItemClick() {

            public void onClick(String item, int position) {
                Toast.makeText(MainActivity.this, item,Toast.LENGTH_SHORT).show();
                currency1.setText(item);
            }
        });
        currency1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCurrency1.showSpinerDialog();
            }
        });

        spinnerCurrency2 = new SpinnerDialog(MainActivity.this,itemsCurrency,"Select Currency");
        spinnerCurrency2.setCancellable(true);
        spinnerCurrency2.setShowKeyboard(false);
        spinnerCurrency2.bindOnSpinerListener(new OnSpinerItemClick() {

            public void onClick(String item, int position) {
                Toast.makeText(MainActivity.this, item,Toast.LENGTH_SHORT).show();
                currency2.setText(item);
            }
        });
        currency2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCurrency2.showSpinerDialog();
            }
        });

        button_res.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String first;
                String one;
                one = (String) currency2.getText();
                first =(String) currency1.getText();
                jsonParse(first,one);
            }
        });

        Customization_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Customization.class);
                startActivity(intent);
            }
        });

        swap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Swap1;
                Swap1 = currency1.getText().toString();
                currency1.setText(currency2.getText());
                currency2.setText(Swap1);
            }
        });
        LoadOption();
        DBRider();
        Log.d("Pars","Вход в бд");
    }

    protected void onDestroy() {
        SaveOption();
        super.onDestroy();
    }


    private void jsonParse(final String a, final String b){
        Log.d("Pars","Вход");
        String url = "http://api.currencylayer.com/live?access_key=791ccd6771bd7c709f97fb37ca83c8dd&%20currencies="+a+","+b+"&format=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONObject last = (JSONObject) response.get("quotes");
                    String nameCurrensy1 = "USD" + b;
                    String nameCurrensy2 = "USD" + a;
                    float res;
                    String numS = num.getText().toString();
                    float numC =Float.parseFloat(numS);
                    numCurrensy1 = (float) last.getDouble(nameCurrensy1);
                    numCurrensy2 = (float) last.getDouble(nameCurrensy2);
                    calculationCurrensy = numCurrensy1 / numCurrensy2;
                    res = calculationCurrensy * numC;
                    String resS = String.format("%.2f",res);
                    mTextViewResult.setText(resS);
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
        mQueue.add(request);
    }

    private void emtyTtemsCurrency(){
        String url = "http://apilayer.net/api/live?access_key=791ccd6771bd7c709f97fb37ca83c8dd";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONObject last = (JSONObject) response.get("quotes");
                    Iterator<String> keys = last.keys();
                    do{
                        String k = keys.next().toString();
                        k = k.substring(3,k.length());
                        itemsCurrency.add(k);
                    }while(keys.hasNext());
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

    public void DBRider(){
        Log.d("Pars","Вход в бд1");
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d("Pars","Вход в бд2");
        String S;
        String B;
        Cursor cursor = db.query(DBHelper.TABLE_CURRENCY, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.d("Pars", "база4");
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY_TO);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_CURRENCY);
            String DB = "";
            do {
                S = cursor.getString(nameIndex);
                B = cursor.getString(emailIndex);
                DB = DB + cursor.getString(nameIndex) + "-" + cursor.getString(emailIndex)+"\n";
                Log.d("Pars1","load1"+ cursor.getString(nameIndex) + " " + cursor.getString(emailIndex)+"\n");
                jsonParseLoad(S, B);
                Log.d("Pars1","load2"+ cursor.getString(nameIndex) + " " + cursor.getString(emailIndex)+"\n");


            } while (cursor.moveToNext());
            //DBtext.setText(DB);
        } else
            Log.d("mLog","0 rows");

        cursor.close();
    }


    private void jsonParseLoad(final String a, final String b){
        Log.d("Pars1","Вход"+ a + b);
        String url = "http://apilayer.net/api/live?access_key=791ccd6771bd7c709f97fb37ca83c8dd";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    JSONObject last = (JSONObject) response.get("quotes");
                    String nameCurrensy1 = "USD" + b;
                    String nameCurrensy2 = "USD" + a;
                    String summRes;
                    String summName;
                    numCurrensy1 = (float) last.getDouble(nameCurrensy1);
                    numCurrensy2 = (float) last.getDouble(nameCurrensy2);
                    calculationCurrensy = numCurrensy1 / numCurrensy2;
                    summName = DBtextName.getText().toString();
                    DBtextName.setText(summName+a+"-"+b+"\n");
                    summRes = DBtextRes.getText().toString();
                    DBtextRes.setText(summRes + String.format("%.2f",calculationCurrensy)+"\n");
                    Log.d("Pars1","Вход7" + a + b +String.format("%.2f",calculationCurrensy));
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
        mQueue.add(request);

        Log.d("Pars","Вход совсем");
    }

    public void SaveOption(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("currency1", currency1.getText().toString());
        ed.putString("currency2", currency2.getText().toString());
        ed.apply();

    }

    public void LoadOption(){
        sPref = getPreferences(MODE_PRIVATE);
        currency1.setText(sPref.getString("currency1", "FROM"));
        currency2.setText(sPref.getString("currency1", "TO"));
    }
}
