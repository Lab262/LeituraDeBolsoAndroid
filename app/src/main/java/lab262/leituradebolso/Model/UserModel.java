package lab262.leituradebolso.Model;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserModel {

    private String id;
    private String email;
    private String token;
    private ArrayList<ReadingModel> readings;

    public static String keyID = "_id";
    public static String keyEmail = "email";
    private static String keyToken = "token";
    public static String keyPassword = "password";

    public UserModel (String email){
        this.email = email;
    }

    public RequestParams getRequestParams(String password) {

        RequestParams dataRequestParams = new RequestParams();
        Map<Object,Object> attributtesHashMap = new HashMap<>();
        Map<Object,Object> informationsHashMap = new HashMap<>();

        informationsHashMap.put(keyEmail,this.getEmail());
        informationsHashMap.put(keyPassword,password);

        attributtesHashMap.put(Requester.keyAttributes,informationsHashMap);

        dataRequestParams.put(Requester.keyData,attributtesHashMap);

        return dataRequestParams;
    }

    public JSONObject getJSONObject(String password) throws JSONException {

        JSONObject dataJsonObject = new JSONObject();
        JSONObject attributtesJsonObject = new JSONObject();
        JSONObject informationsJsonObject = new JSONObject();

        informationsJsonObject.put(keyEmail,this.getEmail());
        informationsJsonObject.put(keyPassword,password);

        attributtesJsonObject.put(Requester.keyAttributes,informationsJsonObject);

        dataJsonObject.put(Requester.keyData,attributtesJsonObject);

        return dataJsonObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ReadingModel> getReadings() {
        return readings;
    }

    public void setReadings(ArrayList<ReadingModel> readings) {
        this.readings = readings;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
