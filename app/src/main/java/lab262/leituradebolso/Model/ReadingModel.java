package lab262.leituradebolso.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by luisresende on 12/09/16.
 */
public class ReadingModel implements Parcelable {

    public String idReading;
    public String title;
    public String author;
    public String duration;
    public String textReading;
    public Boolean isLiked;
    public Boolean isRead;
    public ArrayList<String> emojis;

    public ReadingModel(){

    }

    public ReadingModel(String idReading, String title, String author, String duration, String textReading,
                        ArrayList<String> emojis, Boolean isLiked, Boolean isRead) {
        this.idReading = idReading;
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.textReading = textReading;
        this.emojis = emojis;
        this.isLiked = isLiked;
        this.isRead = isRead;
    }

    private ReadingModel(Parcel parcel){
        idReading = parcel.readString();
        title = parcel.readString();
        author = parcel.readString();
        duration = parcel.readString();
        textReading = parcel.readString();
        isLiked = Boolean.valueOf(parcel.readString());
        isRead =Boolean.valueOf(parcel.readString());
        emojis =  parcel.readArrayList(null);
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
        parcel.writeList(emojis);
    }
}
