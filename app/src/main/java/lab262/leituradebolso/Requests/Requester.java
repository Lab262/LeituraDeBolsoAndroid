package lab262.leituradebolso.Requests;


import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import lab262.leituradebolso.Model.UserModel;

/**
 * Created by luisresende on 27/09/16.
 */

public class Requester {

    public static String baseUrl = "https://leituradebolso.herokuapp.com/api/";
    public static String versionWS = "v0";
    public static String keyData = "data";
    public static String keyAttributes = "attributes";
    public static String keyContentType = "application/json";
    private static String keyAccessToken = "x-access-token";
    public static String keyUrlParamsSkip = "skip";
    public static String keyUrlParamsLimit = "limit";
    private static String keyIDFacebook = "id";
    private static String keyFacebook = "facebook";
    private static String passwordHash = "AQWgd$j[QGe]Bh.Ugkf>?B3y696?2$#B2xwfN3hrVhFrE348g";
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

    public static Header[] getArrayHeaders(String token){
        Header header = new BasicHeader(keyAccessToken,token);
        Header headers[] = new Header[1];
        headers[0] = header;
        return headers;
    }

    public static JSONObject getJSONObject(String email, String password) {

        JSONObject informationsJsonObject = new JSONObject();

        try {
            informationsJsonObject.put(UserModel.keyEmail,email);
            informationsJsonObject.put(UserModel.keyPassword,password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return informationsJsonObject;
    }

    public static JSONObject getJSONObject(String email) {

        JSONObject informationsJsonObject = new JSONObject();

        try {
            informationsJsonObject.put(UserModel.keyEmail,email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return informationsJsonObject;
    }

    public static JSONObject getFacebookJSONObject(String email, String id) {

        JSONObject facebookJsonObject = new JSONObject();
        JSONObject informationsJsonObject = new JSONObject();

        try {
            informationsJsonObject.put(keyIDFacebook,id);
            informationsJsonObject.put(UserModel.keyPassword,passwordHash+id);
            facebookJsonObject.put(UserModel.keyEmail,email);
            facebookJsonObject.put(keyFacebook,informationsJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return facebookJsonObject;
    }
}

