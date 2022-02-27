package com.example.android.covidtracerapp;

public class CovidDetail {
    public String mState;
    //public long mConfirmed;
    CovidDetail(String state/*,long confirmed*/)
    {
        this.mState=state;
      //  this.mConfirmed=confirmed;
    }
    public String getState()
    {
        return mState;
    }
//    public long getConfirmed()
//    {
//        return mConfirmed;
//    }
}
