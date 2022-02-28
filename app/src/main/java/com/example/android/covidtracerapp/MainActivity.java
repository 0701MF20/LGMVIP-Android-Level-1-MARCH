package com.example.android.covidtracerapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    //TextView emptyTextView;
    private static final String LOG=MainActivity.class.getSimpleName();
    public static final String COVID_REQUEST_URL = "https://data.covid19india.org/state_district_wise.json";
    private ListView CovidListView;
    List<CovidDetail> covidDetailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CovidListView=findViewById(R.id.listViewId);
        covidDetailList=new ArrayList<>();
        loadAndParseCovidList(COVID_REQUEST_URL);
        CovidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
            startActivity(intent);
            }
        });
    }
    //For parsing and fetching
    private void  loadAndParseCovidList(String Url) {

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET,Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            Iterator<String> keys=jsonObject.keys();
                            do {
                                String key=keys.next();
                                Log.e("MainActivity","States: "+key);
                                covidDetailList.add(new CovidDetail(key));
                            }while (keys.hasNext());
                        CovidAdapter adapter=new CovidAdapter(getApplicationContext(),covidDetailList);
                        CovidListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.e("MainActivity","JSON Exception",e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}