package com.starnamu.airlineschdule.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class GcmModul  {

    private String SENDER_ID = "313781754169";
    private GoogleCloudMessaging gcm;
    private String regid;

    public GcmModul(Context context){

        gcm = GoogleCloudMessaging.getInstance(context);
        registerInBackground();
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    regid = gcm.register(SENDER_ID);
                    sendRegistrationIdToBackend();
                } catch (IOException ex) {
                }
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
            }
        }.execute(null, null, null);
    }

    private void sendRegistrationIdToBackend() {
        // Your implementation here.
        Log.d("RegID = ", "RegId = " + regid);
    }
}