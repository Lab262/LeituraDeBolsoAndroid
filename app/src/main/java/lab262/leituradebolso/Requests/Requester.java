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
    public static String versionWS = "v0";
    public static Header header;
    public static AsyncHttpClient client = new AsyncHttpClient();

}

