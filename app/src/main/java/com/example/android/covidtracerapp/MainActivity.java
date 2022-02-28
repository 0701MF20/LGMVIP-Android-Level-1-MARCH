package com.example.android.covidtracerapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import android.content.DialogInterface;
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
    List<CovidDetail> covidCityList;
  //  String[] covidCityList;
    static String responses="";
    private int active;
    private int confirmed;
    private int deceased;
    private int recovered;
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
          //      Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
            //startActivity(intent);
                AlertDialog.Builder cityDialog=new AlertDialog.Builder(MainActivity.this);
                cityDialog.setIcon(R.drawable.ic_baseline_arrow_upward_24);
                cityDialog.setTitle("Pick a city");
                DialogOption(responses);
                final String[] listItems= (String[]) covidCityList.toArray();
                cityDialog.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Log.e("MaINaCTIVITY","ItemSelected is"+i);
                    }
                });
                cityDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
AlertDialog customAlertDialog=cityDialog.create();
customAlertDialog.show();
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
private void DialogOption(String availResponse)
{
    try
    {
        JSONObject jsonObject=new JSONObject(availResponse);
        Iterator<String> keys=jsonObject.keys();
        do {
            String key=keys.next();
            JSONObject JsonObject1=new JSONObject(key);
            JSONObject jsonObject2=JsonObject1.getJSONObject("districtData");
            Iterator<String> keysCity=jsonObject2.keys();
                                do {
                                  String key1=keysCity.next();
                                    covidCityList.add(new CovidDetail(key1));
                                }while(keysCity.hasNext());
//                                JSONObject jsonObject3=jsonObject2.getJSONObject("Unassigned");
//                                active=jsonObject3.getInt("active");
//                                confirmed=jsonObject3.getInt("confirmed");
//                                deceased=jsonObject3.getInt("deceased");
//                                recovered=jsonObject3.getInt("recovered");

        }while (keys.hasNext());
        //CovidAdapter adapter=new CovidAdapter(getApplicationContext(),covidDetailList);
        //CovidListView.setAdapter(adapter);
    } catch (JSONException e) {
        Log.e("MainActivity","JSON Exception",e);
    }

}
}