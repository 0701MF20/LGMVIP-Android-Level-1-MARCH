package com.example.android.covidtracerapp;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
public class RecordFragment extends DialogFragment {
    TextView confirmTextView;
    TextView deceasedTextView;
    TextView activeTextView;
    TextView recoveredTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    //    Log.e("MainActivity","Active is:"+MainActivity.active);

        confirmTextView=view.findViewById(R.id.confirmedTextView);
        deceasedTextView=view.findViewById(R.id.deceasedTextView);
        activeTextView=view.findViewById(R.id.activeTextView);
        recoveredTextView=view.findViewById(R.id.recoveredTextView);
activeTextView.setText(MainActivity.active+"");
        confirmTextView.setText(MainActivity.confirmed+"");
        deceasedTextView.setText(MainActivity.deceased+"");
        recoveredTextView.setText(MainActivity.recovered+"");

    }
}
