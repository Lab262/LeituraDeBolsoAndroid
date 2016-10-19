package lab262.leituradebolso.Requests;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Model.UserReadingModel;

/**
 * Created by luisresende on 17/10/16.
 */

public class UserReadingRequest {

    private static String defaultAppendUsersUrl = "/users/";
    private static String defaultAppendReadingsUrl = "/readings";
    private static String defaultAppendReadingOfTheWeekUrl = "/readingsOfTheWeek";

    private static String urlCreate = Requester.baseUrl + Requester.versionWS + defaultAppendUsersUrl;
    private static String urlGetReadingOfTheWeek = Requester.baseUrl + Requester.versionWS + defaultAppendUsersUrl;

    public static void createUserReading(UserReadingModel userReadingModel, String idUser, JsonHttpResponseHandler jsonHttpResponseHandler){

        StringEntity entity = Requester.getStringEntity(userReadingModel.getJSONObject());
        String urlUserReading = urlCreate + idUser + defaultAppendReadingsUrl;

        Requester.client.post(null,urlUserReading,entity,Requester.keyContentType,jsonHttpResponseHandler);
    }

    public static void getAllUserReadings(UserModel userModel, JsonHttpResponseHandler jsonHttpResponseHandler){

        String urlUserReading = urlCreate + userModel.getId() + defaultAppendReadingsUrl;

        Requester.client.get(null,urlUserReading,Requester.getArrayHeaders(userModel.getToken()),null,jsonHttpResponseHandler);

    }

    public static void getUserReadingsOfTheWeek(UserModel userModel, int numberReadings, JsonHttpResponseHandler jsonHttpResponseHandler){

        String urlUserReadingsOfTheWeek = urlGetReadingOfTheWeek + userModel.getId() + defaultAppendReadingOfTheWeekUrl;

        RequestParams requestParams = new RequestParams();
        requestParams.put(Requester.keyUrlParamsSkip,0);
        requestParams.put(Requester.keyUrlParamsLimit,numberReadings);

        Requester.client.get(null,urlUserReadingsOfTheWeek,Requester.getArrayHeaders(userModel.getToken()),requestParams,jsonHttpResponseHandler);

    }

    public static void updateUserReadings(UserModel userModel, UserReadingModel userReadingModel, JsonHttpResponseHandler jsonHttpResponseHandler){

        String urlUserReading = urlCreate + userModel.getId() + defaultAppendReadingsUrl + "/" + userReadingModel.getIdReading() + "/";


        //"{\"data\": {\"attributes\": {\"alreadyRead\": true,\"isFavorite\": true}}}"

        StringEntity entity = Requester.getStringEntity(userReadingModel.getJSONObject());

        Requester.client.patch(null,urlUserReading,Requester.getArrayHeaders(userModel.getToken()),entity,Requester.keyContentType,jsonHttpResponseHandler);

    }



}
