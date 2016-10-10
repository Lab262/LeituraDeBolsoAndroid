package lab262.leituradebolso.Model;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserModel extends RealmObject {

    @PrimaryKey
    private String id;

    private String email;
    private String token;
    private RealmList<ReadingModel> readings;

    private long lastSessionTimeInterval;

    public static String keyID = "_id";
    public static String keyEmail = "email";
    public static String keyPassword = "password";
    private static String keyToken = "token";
    private static String keyModel = "user";

    public UserModel (){

    }

    public UserModel (JSONObject jsonObject){
        setObject(jsonObject);
        setLastSessionTimeInterval(0);
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

    public long getLastSessionTimeInterval() {
        return lastSessionTimeInterval;
    }

    public void setLastSessionTimeInterval(long lastSessionTimeInterval) {
        this.lastSessionTimeInterval = lastSessionTimeInterval;
    }

    public void updateLastSessionTimeInterval(long lastSessionTimeInterval) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.lastSessionTimeInterval = lastSessionTimeInterval;
        realm.commitTransaction();
    }

    public int getDifferenceBetweenDateNow(){
        if (this.getLastSessionTimeInterval()!=0){

            //Get calendar day user
            Calendar calendarUser = Calendar.getInstance();
            calendarUser.setTimeInMillis(this.getLastSessionTimeInterval());

            //Get calendar actual date
            Calendar calendarActualDate = Calendar.getInstance();

            int dayUser = calendarUser.get(Calendar.DAY_OF_YEAR);
            int dayActualDate = calendarActualDate.get(Calendar.DAY_OF_YEAR);
            int differenceDates = dayActualDate - dayUser;

            return differenceDates;
        }else {
            return -1;
        }
    }
}
