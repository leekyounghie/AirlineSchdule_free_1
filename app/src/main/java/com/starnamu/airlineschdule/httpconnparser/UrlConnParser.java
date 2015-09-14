package com.starnamu.airlineschdule.httpconnparser;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.starnamu.airlineschdule.comm.AirLineItems;
import com.starnamu.airlineschdule.comm.CommonConventions;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class UrlConnParser implements CommonConventions {
    String Aurl = "http://openapi.airport.kr/openapi/service/StatusOfPassengerFlights/getPassengerArrivals?" +
            "ServiceKey=RN5il12RYM%2FXFWaIm8otCbez%2B5W1YxN91ZzBtYx4u3hh24IgLuMAr5L" +
            "EvByuM62KPv7l8Y4qbNUy0AgE2YtWHw%3D%3D";

    String[] ADStates;

    Context mContext;
    ArrayList<AirlineItem> items;
    StringBuffer StrBuffer=new StringBuffer();

    public UrlConnParser(Context context) {
        this.mContext = context;
        ADStates = new String[]{"A", "D"};
        GetDataTask getDataTask = new GetDataTask();
        getDataTask.execute(Aurl);

    }

    private class GetDataTask extends AsyncTask<String, Void, InputStream> {
        ProgressDialog dialog;
        String AinStream, DinStream;
        String str;
        int countStr = 0;

        @Override
        protected InputStream doInBackground(String... params) {
            OkHttpExample okHttpExample = new OkHttpExample();
            try {
                AinStream = okHttpExample.doGetStringRequest(Aurl);
                str = okHttpExample.doGetStringRequest(Aurl);
                custonXmlParser();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        void custonXmlParser() throws Exception {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(str));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
             /*   if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.i("TAG", "Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    Log.i("TAG", "Start tag " + xpp.getName());
                } else*/
                if (eventType == XmlPullParser.TEXT) {
//                    Log.i("TAG", xpp.getText());
                    StrBuffer.append(xpp.getText());

                }/* else if (eventType == XmlPullParser.END_TAG) {
                    Log.i("TAG", "End tag " + xpp.getName());
                }*/

                eventType = xpp.next();
            }
            System.out.println("End document");
        }


        public void customParser(String str) {
            Log.i("TAG", str);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setTitle("파일을 다운로드중");
            dialog.setMessage("잠시만 기다리세요...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            dialog.dismiss();
            customParser(StrBuffer.toString());
            AirLineItems airLineItems = AirLineItems.getNewInstance();
            airLineItems.setItems(items);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(InputStream inputStream) {
            super.onCancelled(inputStream);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public class OkHttpExample {

        OkHttpClient client = new OkHttpClient();

        // code request code here
        InputStream doGetStreamRequest(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().byteStream();
        }

        String doGetStringRequest(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
}

