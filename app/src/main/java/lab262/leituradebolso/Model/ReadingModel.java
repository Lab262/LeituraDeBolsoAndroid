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
import lab262.leituradebolso.Requests.Requester;

/**
 * Created by luisresende on 12/09/16.
 */
public class ReadingModel extends RealmObject implements Parcelable {

    //Keys in WS
    public static String keyID = "-id";
    public static String keyTitle = "title";
    public static String keyAuthor = "author-name";
    public static String keyDuration = "time-to-read-in-minutes";
    public static String keyTextReading = "content";
    public static String keyEmojis = "emojis";

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
            this.duration = jsonObject.getString(keyDuration);
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

    private ReadingModel(Parcel parcel){
        idReading = parcel.readString();
        title = parcel.readString();
        author = parcel.readString();
        duration = parcel.readString();
        textReading = parcel.readString();
        isLiked = Boolean.valueOf(parcel.readString());
        isRead =Boolean.valueOf(parcel.readString());
//        emojis =  parcel.readTypedList(null);
    }


    static public String getEmijoByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public static final Parcelable.Creator<ReadingModel> CREATOR = new Creator<ReadingModel>() {

        public ReadingModel createFromParcel(Parcel source) {
            return new ReadingModel(source);
        }

        public ReadingModel[] newArray(int size){
            return new ReadingModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idReading);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(duration);
        parcel.writeString(textReading);
        parcel.writeString(isLiked.toString());
        parcel.writeString(isRead.toString());
        //parcel.writeList(emojis);
    }
}
