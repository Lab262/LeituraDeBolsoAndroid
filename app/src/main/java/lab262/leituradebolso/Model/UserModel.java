package lab262.leituradebolso.Model;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

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

    public RequestParams getRequestParams(){
        RequestParams requestParams = new RequestParams();
        requestParams.add(keyID,this.getId());
        requestParams.add(keyEmail,this.getEmail());
        requestParams.add(keyToken,this.getToken());
        return requestParams;
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
