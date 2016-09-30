package lab262.leituradebolso.Model;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserModel extends RealmObject {

    private String id;
    private String email;
    private String token;
    private RealmList<ReadingModel> readings;

    public static String keyID = "_id";
    public static String keyEmail = "email";
    public static String keyPassword = "password";
    private static String keyToken = "token";
    private static String keyModel = "user";

    public UserModel (){

    }

    public UserModel (JSONObject jsonObject){
        setObject(jsonObject);
    }

    public UserModel (String email){
        this.email = email;
    }

    public JSONObject getJSONObject(String password) {

        JSONObject dataJsonObject = new JSONObject();
        JSONObject attributtesJsonObject = new JSONObject();
        JSONObject informationsJsonObject = new JSONObject();

        try {
            informationsJsonObject.put(keyEmail,this.getEmail());
            informationsJsonObject.put(keyPassword,password);
            attributtesJsonObject.put(Requester.keyAttributes,informationsJsonObject);
            dataJsonObject.put(Requester.keyData,attributtesJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataJsonObject;
    }

    public void setObject(JSONObject jsonObject){
        try {
            JSONObject user = (JSONObject)jsonObject.get(keyModel);

            this.setToken(jsonObject.getString(keyToken));
            this.setId(user.getString(keyID));
            this.setEmail(user.getString(keyEmail));
            //TODO: Serializer para ler as leituras
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<ReadingModel> getReadings() {
        return readings;
    }

    public void setReadings(RealmList<ReadingModel> readings) {
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
