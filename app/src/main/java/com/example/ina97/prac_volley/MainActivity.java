package com.example.ina97.prac_volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends Activity {
    Button bt;
    TextView tv,tv2,tv3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://tronzephp.me/temp.php";

        bt = (Button) findViewById(R.id.bt);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3= (TextView)findViewById(R.id.tv3);

        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(), networkSuccessListener(), networkErrorListener());

        final StringRequest request = new StringRequest(Request.Method.POST, url,
                //요청 성공
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        tv3.setText(response);
                    }
                },
                //요청 실패
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        tv3.setText("Error");
                    }
                });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(objectRequest);
                queue.add(request);
            }
        });

    }


    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                String valStr = null;
                String keyStr = null;
                Iterator <String> key =  response.keys();
                try{
                    keyStr = key.next();
                    valStr = response.getString(keyStr);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                tv.setText("Key = "+keyStr);
                tv2.setText("Value = " + valStr);
            }
        };
    }

    private Response.ErrorListener networkErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("Error");
            }
        };
    }

}