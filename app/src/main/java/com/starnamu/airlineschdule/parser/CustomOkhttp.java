package com.starnamu.airlineschdule.parser;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by star_namu on 2015-09-16.
 */
public class CustomOkhttp {

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
