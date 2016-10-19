package lab262.leituradebolso.Model;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 17/10/16.
 */

public class UserReadingModel extends RealmObject {

    @PrimaryKey
    private String idReading;

    private Boolean isFavorite;
    private Boolean isRead;


    public static String keyID = "reading-id";
    public static String keyIsRead = "already-read";
    public static String keyIsFavorite = "is-favorite";


    public UserReadingModel (){

    }

    public UserReadingModel (String idReading){
        this.setIdReading(idReading);
        this.setIsRead(false);
        this.setFavorite(false);
    }

    public UserReadingModel (JSONObject jsonObject){
        setObject(jsonObject);
    }


    public JSONObject getJSONObject() {

        JSONObject dataJsonObject = new JSONObject();
        JSONObject attributtesJsonObject = new JSONObject();
        JSONObject informationsJsonObject = new JSONObject();

        try {
            informationsJsonObject.put(keyID,this.getIdReading());
            informationsJsonObject.put(keyIsRead,this.getIsRead());
            informationsJsonObject.put(keyIsFavorite,this.getFavorite());
            attributtesJsonObject.put(Requester.keyAttributes,informationsJsonObject);
            dataJsonObject.put(Requester.keyData,attributtesJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataJsonObject;
    }

    public void setObject(JSONObject jsonObject){
        try {
            this.setIdReading(jsonObject.getString(keyID));
            this.setFavorite(jsonObject.getBoolean(keyIsFavorite));
            this.setIsRead(jsonObject.getBoolean(keyIsRead));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIdReading() {
        return idReading;
    }

    public void setIdReading(String idReading) {
        this.idReading = idReading;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public void updateIsRead(Boolean isRead) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.isRead = isRead;
        realm.commitTransaction();
    }

    public void updateIsFavorite(Boolean isRead) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.isFavorite = isRead;
        realm.commitTransaction();
    }
}
