//Not used till now
package com.example.android.covidtracerapp;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG=QueryUtils.class.getSimpleName();
    public static List<CovidDetail> fetchFromServer(String covidUrl)/*String urlll*/
    {
        String JSONstring="";
        try
        {
            JSONstring=RequestForCovidData(covidUrl);
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"IOException",e);
        }
        List<CovidDetail> covidRecords=extractFeatures(JSONstring);
        return covidRecords;
    }



    private static String RequestForCovidData(String urls) throws IOException {
        final String json = "";
        if(urls==null)
        {
            return json[0];
        }

//            // Defining the Volley request queue that handles the URL request concurrently
//            RequestQueue requestQueue=Volley.newRequestQueue(QueryUtils.class);
//            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(urls, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    // This string will hold the results
//                    data.append(response.toString());
//                }
//            },null);
//            if(data.equals(""))
//            {
//                return null;
//            }
//            return data.toString();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                  //      textView.setText("Response is: " + response.substring(0,500));
                    json =response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.e(LOG_TAG,"That didn't work!");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private String RetrievingWholeJsonData(String urll) {
        StringBuffer data=new StringBuffer("");
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      if(response!=null&&!response.isEmpty())
                      {
                          data.append(response);
                      }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                    Log.e(LOG_TAG,"Error arrises");
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    private static List<CovidDetail> extractFeatures(String JSONString)
    {
List<CovidDetail> covidDetailList=new ArrayList<>();
try
{
    JSONObject jsonObject=new JSONObject(JSONString);
    Iterator<String> keys=jsonObject.keys();
   do {
       String key=keys.next();
       Log.e(LOG_TAG,"States: "+key);
       covidDetailList.add(new CovidDetail(key));
   }while (keys.hasNext());
} catch (JSONException e) {
    Log.e("QueryUtil","JSON Exception",e);
}
return covidDetailList;
    }
}

