package lab262.leituradebolso.Requests;

import android.os.Looper;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import lab262.leituradebolso.Enums.RequestType;

/**
 * Created by luisresende on 27/09/16.
 */

public class Requester {

    public static String baseUrl = "https://leituradebolso.herokuapp.com/api/";
    public static String versionWS = "v0";
    public static String keyData = "data";
    public static String keyAttributes = "attributes";
    public static String keyContentType = "application/json";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static StringEntity getStringEntity(JSONObject jsonObject){

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonObject.toString());
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return entity;
    }
}

