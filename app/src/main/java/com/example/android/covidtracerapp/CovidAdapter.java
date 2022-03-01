package com.example.android.covidtracerapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
public class CovidAdapter extends ArrayAdapter<CovidDetail>
{
    CovidAdapter(Context context, List<CovidDetail> covidDetails)
    {
        super(context,0, covidDetails);
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        View listView=convertView;
        if(listView==null)
        {
            listView= LayoutInflater.from(getContext()).inflate(R.layout.testing,parent,false);
        }
        CovidDetail covidRecord=getItem(position);
        TextView stateText=(TextView)listView.findViewById(R.id.stateTextViewId);
        stateText.setText(covidRecord.getState()+"");

        return listView;
    }


}
