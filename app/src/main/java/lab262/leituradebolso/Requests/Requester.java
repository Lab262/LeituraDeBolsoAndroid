package lab262.leituradebolso.Requests;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Enums.RequestType;

/**
 * Created by luisresende on 27/09/16.
 */

public class Requester {

    public static String baseUrl = "https://leituradebolso.herokuapp.com/api/";
//    public static String baseUrl = "https://172.16.1.105:8080/api/";
    public static String versionWS = "v0";
    public static String keyData = "data";
    public static String keyAttributes = "attributes";
    public static AsyncHttpClient client = new AsyncHttpClient();

//    public static AsyncHttpClient getClient(){
//        if (client==null){
//            client = new AsyncHttpClient();
//            client.addHeader("Content-Type", "application/json");
//        }
//
//        return client;
//    }

}

