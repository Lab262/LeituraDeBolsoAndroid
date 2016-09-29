package lab262.leituradebolso.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import lab262.leituradebolso.Model.UserModel;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserRequest {

    private static String urlCreate = Requester.baseUrl + Requester.versionWS + "/users";
    private static String urlLogin = Requester.baseUrl + Requester.versionWS + "/auth/login";
    private static String urlForgot = Requester.baseUrl + Requester.versionWS + "/auth/forgotPassword";

    public static void createAccountUser(UserModel userModel, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(userModel.getJSONObject(password));

        Requester.client.post(null,urlCreate,entity,Requester.keyContentType,jsonHttpResponseHandler);
    }

    public static void loginUser(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = new RequestParams();
        requestParams.add(UserModel.keyPassword,password);
        requestParams.add(UserModel.keyEmail, email);

        Requester.client.post(urlLogin,requestParams,jsonHttpResponseHandler);

    }

    public static void forgotUser(String email, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = new RequestParams();
        requestParams.add(UserModel.keyEmail, email);

        Requester.client.post(urlForgot,requestParams,jsonHttpResponseHandler);

    }

}
