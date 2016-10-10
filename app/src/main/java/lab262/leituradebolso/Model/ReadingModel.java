package lab262.leituradebolso.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lab262.leituradebolso.Requests.Requester;

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

    @PrimaryKey
    public String idReading;


    public String title;
    public String author;
    public String duration;
    public String textReading;
    public Boolean isLiked;
    public Boolean isRead;
    public RealmList<EmojiModel> emojis;

    public ReadingModel(){

    }

    public ReadingModel(JSONObject jsonObject) {

        try {
            this.idReading = jsonObject.getString(keyID);
            this.title = jsonObject.getString(keyTitle);
            this.author = jsonObject.getString(keyAuthor);
            this.duration = jsonObject.getString(keyDuration) + " min";
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
}
