package com.example.android.covidtracerapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    private static final String LOG = MainActivity.class.getSimpleName();
    public static final String COVID_REQUEST_URL = "https://data.covid19india.org/state_district_wise.json";
    private ListView CovidListView;
    List<Model> covidDetailList;
    ArrayList<String> covidCity= new ArrayList<String>();
    static String responses;
    public static int active;
    public static int confirmed;
    public static int deceased;
    public static int recovered;
    CovidAdapter adapter;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CovidListView = findViewById(R.id.listViewId);
        covidDetailList = new ArrayList<>();
        loadAndParseCovidList(COVID_REQUEST_URL);
        final int[] checkedItem = {-1};
        CovidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model adapts=adapter.getItem(position);
               String state=adapts.getState();
                Toast.makeText(getApplicationContext(),"State is:"+state,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder cityDialog = new AlertDialog.Builder(MainActivity.this);
                cityDialog.setIcon(R.drawable.ic_baseline_arrow_upward_24);
                cityDialog.setTitle("Pick City");

                try {
                    JSONObject object = jsonObject.getJSONObject(state);
                    JSONObject districtData = object.getJSONObject("districtData");
                    Iterator<String> keysCity = districtData.keys();
                    while (keysCity.hasNext()) {
                        String chars= keysCity.next();
                        covidCity.add(chars);
                    }
                }
                catch (JSONException e) {
                }

                String[] str = new String[covidCity.size()];
                for (int i = 0; i < covidCity.size(); i++) {
                    str[i] = covidCity.get(i);
                }

                covidCity.clear();

                cityDialog.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        int selectedPosition = ((AlertDialog) dialogInterface).getListView().getCheckedItemPosition();
                   String city=str[position];

                        try {
                            JSONObject object = jsonObject.getJSONObject(state);
                            JSONObject districtData = object.getJSONObject("districtData");
                            JSONObject cityFinal=districtData.getJSONObject(city);
                                active=cityFinal.getInt("active");
                               confirmed=cityFinal.getInt("confirmed");
                              deceased=cityFinal.getInt("deceased");
                                recovered=cityFinal.getInt("recovered");
                        }
                        catch (JSONException e) {
                        }
           RecordFragment fragment=new RecordFragment();
           fragment.show(getSupportFragmentManager(),"RecordFragment");
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = cityDialog.create();
                mDialog.show();
            }
        });



}
    //For parsing and fetching
    private void loadAndParseCovidList(String Url) {

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responses=response;
                        try {
                             jsonObject = new JSONObject(response);
                            Iterator<String> keys = jsonObject.keys();
                            do {
                                String key = keys.next();
                                Log.e("MainActivity", "States: " + key);
                                covidDetailList.add(new Model(key));

                            } while (keys.hasNext());
                           adapter = new CovidAdapter(getApplicationContext(), covidDetailList);
                            CovidListView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.e("MainActivity", "JSON Exception", e);
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