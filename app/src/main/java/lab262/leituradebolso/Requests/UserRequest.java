package lab262.leituradebolso.Requests;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Model.UserModel;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserRequest {

    private static String url = Requester.baseUrl + "v0/users";

    public static void createAccountUser(UserModel userModel, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = userModel.getRequestParams();
        requestParams.add(UserModel.keyPassword,password);

        Requester.client.post(url,requestParams,jsonHttpResponseHandler);

    }

    public static void loginUser(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = new RequestParams();
        requestParams.add(UserModel.keyPassword,password);
        requestParams.add(UserModel.keyEmail, email);

        Requester.client.post(url,requestParams,jsonHttpResponseHandler);

    }

    public static void forgotUser(String email, JsonHttpResponseHandler jsonHttpResponseHandler){

        RequestParams requestParams = new RequestParams();
        requestParams.add(UserModel.keyEmail, email);

        Requester.client.post(url,requestParams,jsonHttpResponseHandler);

    }

}
