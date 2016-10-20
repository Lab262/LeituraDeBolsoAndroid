package lab262.leituradebolso.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.entity.StringEntity;
import lab262.leituradebolso.Model.UserModel;


/**
 * Created by luisresende on 27/09/16.
 */

public class UserRequest {

    private static String urlCreate = Requester.baseUrl + Requester.versionWS + "/users";
    private static String urlLogin = Requester.baseUrl + Requester.versionWS + "/auth/login";
    private static String urlLoginFacebook = Requester.baseUrl + Requester.versionWS + "/auth/facebook";
    private static String urlForgot = Requester.baseUrl + Requester.versionWS + "/auth/forgotPassword";

    public static String keyMessageForgotPassword = "message";
    public static String keyFieldsLoginFacebook = "fields";
    public static String keyFieldEmailFacebook = "email";
    public static String keyFieldIDFacebook = "id";

    public static void createAccountUser(UserModel userModel, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(userModel.getJSONObject(password));

        Requester.client.post(null,urlCreate,entity,Requester.keyContentType,jsonHttpResponseHandler);
    }

    public static void loginUser(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(Requester.getJSONObject(email, password));

        Requester.client.post(null,urlLogin,entity,Requester.keyContentType,jsonHttpResponseHandler);

    }

    public static void loginFacebookUser(String email, String idFacebook, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(Requester.getFacebookJSONObject(email, idFacebook));

        Requester.client.post(null,urlLoginFacebook,entity,Requester.keyContentType,jsonHttpResponseHandler);

    }

    public static void forgotUser(String email, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(Requester.getJSONObject(email));

        Requester.client.post(null,urlForgot,entity,Requester.keyContentType,jsonHttpResponseHandler);

    }

}
