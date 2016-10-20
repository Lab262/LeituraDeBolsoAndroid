package lab262.leituradebolso.Model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 27/09/16.
 */

public class UserModel extends RealmObject {

    //Variables in WS
    @PrimaryKey
    private String id;

    private String email;
    private String token;

    //Local Variables
    private long lastSessionTimeInterval;
    private Boolean reciveNotification;
    private Boolean noturneMode;
    private int textSize;
    private Date hourNotification;


    public static String keyID = "_id";
    public static String keyEmail = "email";
    public static String keyPassword = "password";
    private static String keyToken = "token";
    private static String keyModel = "user";

    private static int defaultTextSize = 14;
    private static int defaultTimeInterval = 0;

    public UserModel (){

    }

    public UserModel (JSONObject jsonObject){
        setObject(jsonObject);
        setDefaultPropertys();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultPropertys(){
        setLastSessionTimeInterval(defaultTimeInterval);
        setReciveNotification(true);
        setNoturneMode(false);
        setTextSize(defaultTextSize);
        setHourNotification(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getHourNotification() {
        return hourNotification;
    }

    public void setHourNotification(Date hourNotification) {
        this.hourNotification = hourNotification;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Boolean getNoturneMode() {
        return noturneMode;
    }

    public void setNoturneMode(Boolean noturneMode) {
        this.noturneMode = noturneMode;
    }

    public Boolean getReciveNotification() {
        return reciveNotification;
    }

    public void setReciveNotification(Boolean reciveNotification) {
        this.reciveNotification = reciveNotification;
    }

    public void updateHourNotification(Date hourNotification) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.hourNotification = hourNotification;
        realm.commitTransaction();
    }

    public void updateTextSize(int textSize) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.textSize = textSize;
        realm.commitTransaction();
    }

    public void updateNoturneMode(Boolean noturneMode) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.noturneMode = noturneMode;
        realm.commitTransaction();
    }

    public void updateReciveNotification(Boolean reciveNotification) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.reciveNotification = reciveNotification;
        realm.commitTransaction();
    }

    public void logoutUser() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.token = null;
        realm.commitTransaction();
    }

    public void updateLastSessionTimeInterval(long lastSessionTimeInterval) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.lastSessionTimeInterval = lastSessionTimeInterval;
        realm.commitTransaction();
    }

    public void updateToken(String token) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.token = token;
        realm.commitTransaction();
    }

    public int getDifferenceBetweenDateNow(){
        if (this.getLastSessionTimeInterval()!=defaultTimeInterval){

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
            return 1;
        }
    }
}
