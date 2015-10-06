package com.starnamu.airlineschdule.parser;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.starnamu.airlineschdule.MainActivity;
import com.starnamu.airlineschdule.airlinealarmitemgroup.InterestFlightUpdate;
import com.starnamu.airlineschdule.comm.AirLineItems;
import com.starnamu.airlineschdule.comm.CommonConventions;
import com.starnamu.airlineschdule.noticetext.NoticeTextClass;
import com.starnamu.airlineschdule.slidinglayout.AirlineItem;
import com.starnamu.projcet.airlineschedule.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class AirlineParser implements CommonConventions {

    Element[] element;
    String[] itemStr;
    ArrayList<AirlineItem> TempList;
    ArrayList<AirlineItem> itemLists;
    String AUrl, DUrl;
    String ADstate;
    Handler handler;
    CustomOkhttp customOkhttp;

    public AirlineParser() {

        this.element = new Element[PARSERITEMGROUP.length];
        this.itemStr = new String[PARSERITEMGROUP.length];

        customOkhttp = new CustomOkhttp();

        AUrl = URLHADE + PARRIVALS + SERVICEKEY;
        DUrl = URLHADE + PDEPARTURES + SERVICEKEY;

        handler = new Handler();
        TempList = new ArrayList<>();

        GetDataTask getDataTask = new GetDataTask();
        getDataTask.execute(AUrl, DUrl);
    }

    private class GetDataTask extends AsyncTask<String, Void, InputStream> {
        int i = 0;
        InputStream inStream;
        URL url;
        View view;
        long LastCurrentTime;

        @Override
        protected InputStream doInBackground(String... params) {

            try {
                inStream = customOkhttp.doGetStreamRequest(params[0]);
                i++;
                Log.i("URL호출 횟수 : ", params[0]);
                ADstate = "A";
                airportparser(inStream);

                url = new URL(params[1]);
                inStream = url.openStream();

               /* inStream = customOkhttp.doGetStreamRequest(params[1]);*/
                i++;
                Log.i("URL호출 횟수 : ", Integer.toString(i));
                ADstate = "D";
                Log.i("INSTREAM", inStream.toString());
                airportparser(inStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("onPreExecute", "Strar");
            LayoutInflater inflater = (LayoutInflater) MainActivity.getMainActivityContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_intro, null, false);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            ((MainActivity) MainActivity.getMainActivityContext()).addContentView(view, layoutParams);
            TextView NoticeText = (TextView) view.findViewById(R.id.NoticeText);
            NoticeText.setText(getNoticeMessage());
            view.setVisibility(View.VISIBLE);

            LastCurrentTime = System.currentTimeMillis();
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);

            Log.i("onPostExecute", "Strar");

            AirLineItems airLineItems = AirLineItems.getNewInstance();
            airLineItems.setItems(getArrayList());
            new InterestFlightUpdate(getArrayList());
            Long NewCurrentTime;
            while (true) {
                NewCurrentTime = System.currentTimeMillis();
                Log.i("Delay Time", Long.toString(NewCurrentTime));
                if (LastCurrentTime + 5000 <= NewCurrentTime) {
                    view.setVisibility(View.GONE);
                    ((MainActivity) MainActivity.getMainActivityContext()).adapter.notifyDataSetChanged();

                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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

    public String getNoticeMessage(){
        NoticeTextClass noticeTextClass = new NoticeTextClass();
        return noticeTextClass.getText();
    }

    /*외부로 부터이 요청에 ArrayList 반환 */
    public ArrayList<AirlineItem> getArrayList() {
        return this.itemLists;
    }

    public void airportparser(InputStream inStream) throws Exception {
        Log.i("airportparser", "Strar");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);

        this.itemLists = parserDocument(document);
    }

    private ArrayList<AirlineItem> parserDocument(Document document) {
        Log.i("parserDocument", "Strar");

        Element element = document.getDocumentElement();
        element.getElementsByTagName("item");
        NodeList nodeList = element.getElementsByTagName("item");

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (parseItemNode(nodeList, i) != null) {
                    AirlineItem item = parseItemNode(nodeList, i);
                    TempList.add(item);
                }
            }
        }
        Log.i("parseItemNode", "Strar");
        return TempList;
    }

    private AirlineItem parseItemNode(NodeList nodeList, int index) {


        Element elem = (Element) nodeList.item(index);
        for (int i = 0; i < PARSERITEMGROUP.length; i++) {
            element[i] = (Element) elem.getElementsByTagName(PARSERITEMGROUP[i]).item(0);
            if (element[i] == null) {
                itemStr[i] = " ";
            } else if (element[i] != null) {
                Node firstchild = element[i].getFirstChild();
                if (firstchild != null) {
                    itemStr[i] = firstchild.getNodeValue();
                }
            }
        }

        if (ADstate.equals("A")) {
            itemStr[10] = "A";
        } else if (ADstate.equals("D")) {
            itemStr[10] = "D";
        } else {
            itemStr[10] = "";
        }
        if (airlineCheck(itemStr[0])) {
            AirlineItem item = new AirlineItem(itemStr);
            return item;
        }
        return null;
    }


    private boolean airlineCheck(String airline) {
        for (int i = 0; i < ALLAIRLINENAME.length; i++) {
            if (airline.equals(ALLAIRLINENAME[i])) {
                return true;
            }
        }
        return false;
    }
}