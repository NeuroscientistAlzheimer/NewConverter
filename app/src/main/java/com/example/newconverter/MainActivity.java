package com.example.newconverter;

import androidx.appcompat.app.AppCompatActivity;

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

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private TextView mTextViewResult;
    SpinnerDialog spinnerCurrency1;
    SpinnerDialog spinnerCurrency2;
    Button currency1;
    Button currency2;
    Button button_res;
    EditText num;
    float numCurrensy1;
    float numCurrensy2;
    float calculationCurrensy;
    float number;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewResult = findViewById(R.id.text_view_result);
        mQueue = Volley.newRequestQueue(this);
        Log.d("Pars","Начало");
        Log.d("Pars","Конец");
        final ArrayList<String> itemsCurrency = new ArrayList<>();
        itemsCurrency.add("USD");
        itemsCurrency.add("RUB");
        itemsCurrency.add("EUR");
        itemsCurrency.add("AUD");
        currency1 = findViewById(R.id.currency1);
        currency2 = findViewById(R.id.currency2);
        button_res = findViewById(R.id.button_res);
        num = findViewById(R.id.num);

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
                Log.d("Pars","кнопка");
                String first;
                String one;
                one = (String) currency2.getText();
                first =(String) currency1.getText();
                Log.d("Pars","Начало" + one + first);
                jsonParse(first,one);
            }
        });

    }


    private void jsonParse(final String a, final String b){
        Log.d("Pars","Вход");
        String url = "http://api.currencylayer.com/live?access_key=ad16a4ab373cbeca1bdbf0fd5b1e77ed&%20currencies="+a+","+b+"&format=1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Pars", "присвоение");
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
                    Log.d("Pars", "присвоение"+ numC +"\n"+numCurrensy1+"\n"+numCurrensy2 + "\n" + calculationCurrensy + "\n" + resS);
                    mTextViewResult.setText(resS);
                    Log.d("Pars", "завершение" );
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
}
