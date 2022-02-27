//Not used till now
package com.example.android.covidtracerapp;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class CovidLoader extends AsyncTaskLoader {
    private String mUrl;
    private List<CovidDetail> listOfCovidRecords;
    public CovidLoader(@NonNull Context context,String UniformRL) {
        super(context);
        mUrl=UniformRL;
    }

    @Override
    protected void onStartLoading() {
//        super.onStartLoading();
    forceLoad();
    }

    @Nullable
    @Override
    public List<CovidDetail> loadInBackground() {
        if(mUrl==null)
        {
            return null;
        }
        else
        {
            listOfCovidRecords=QueryUtils.fetchFromServer(getContext(),mUrl);
     return listOfCovidRecords;
        }
    }
}
