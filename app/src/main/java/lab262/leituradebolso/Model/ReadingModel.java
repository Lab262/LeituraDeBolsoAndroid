package lab262.leituradebolso.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import lab262.leituradebolso.Persistence.DBManager;

/**
 * Created by luisresende on 12/09/16.
 */
public class ReadingModel extends RealmObject {

    //Keys in WS
    public static String keyID = "-id";
    public static String keyTitle = "title";
    public static String keyAuthor = "author-name";
    public static String keyDuration = "time-to-read-in-minutes";
    public static String keyTextReading = "content";
    public static String keyEmojis = "emojis";

    public static String keyModelSelected = "modelreading";
    private static String defaultAppendDuration = " min";

    @PrimaryKey
    public String idReading;


    public String title;
    public String author;
    public String duration;
    public String textReading;
    public RealmList<EmojiModel> emojis;

    public ReadingModel(){

    }

    public ReadingModel(JSONObject jsonObject) {

        try {
            this.idReading = jsonObject.getString(keyID);
            this.title = jsonObject.getString(keyTitle);
            this.author = jsonObject.getString(keyAuthor);
            this.duration = jsonObject.getString(keyDuration) + defaultAppendDuration;
            this.textReading = jsonObject.getString(keyTextReading);

            JSONArray jsonArrayEmojis = jsonObject.getJSONArray(ReadingModel.keyEmojis);
            RealmList<EmojiModel> emojiModels = new RealmList<>();
            for (int j=0; j<jsonArrayEmojis.length(); j++) {
                EmojiModel emojiModel = new EmojiModel(jsonArrayEmojis.getString(j));
                emojiModels.add(emojiModel);
            }
            this.emojis = emojiModels;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ReadingModel[] getAllReadingData() {
        RealmResults<ReadingModel> realmResults = (RealmResults<ReadingModel>) DBManager.getAll(ReadingModel.class);
        return getReadingsData(realmResults);
    }

    public static ReadingModel[] getTannedReadingData() {
        RealmResults<UserReadingModel> realmResults = (RealmResults<UserReadingModel>)
                DBManager.getAllByParameter(UserReadingModel.class,UserReadingModel.nameParameterIsFavorite,true);

        if (realmResults.size()>0){
            return getReadingsDataWithUserReadings(realmResults);
        }else {
            return new ReadingModel[0];
        }
    }

    public static ReadingModel[] getNotReadReadingData() {
        RealmResults<UserReadingModel> realmResults = (RealmResults<UserReadingModel>)
                DBManager.getAllByParameter(UserReadingModel.class,UserReadingModel.nameParameterIsRead,false);

        if (realmResults.size()>0){
            return getReadingsDataWithUserReadings(realmResults);
        }else {
            return new ReadingModel[0];
        }
    }

    public static ReadingModel[] getReadingsDataWithUserReadings(RealmResults<UserReadingModel> realmResults){
        ArrayList<String> arrayListIDs = new ArrayList<>();

        for (UserReadingModel userReadingModel : realmResults){
            arrayListIDs.add(userReadingModel.getIdReading());
        }
        RealmResults<ReadingModel> readingModelRealmResults = (RealmResults<ReadingModel>)
                DBManager.getAllByParameter(ReadingModel.class,UserReadingModel.nameParameterIDReading,arrayListIDs);
        return getReadingsData(readingModelRealmResults);
    }

    public static ReadingModel[] getReadingsData(RealmResults<ReadingModel> realmResults){

        ReadingModel[] readingsData = new ReadingModel[realmResults.size()];

        for (int i=0; i<realmResults.size(); i++){
            readingsData[i] = realmResults.get(i);
        }

        return readingsData;
    }
}
