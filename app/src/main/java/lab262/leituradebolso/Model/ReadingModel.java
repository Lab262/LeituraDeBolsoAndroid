package lab262.leituradebolso.Model;

import java.util.ArrayList;

/**
 * Created by luisresende on 12/09/16.
 */
public class ReadingModel {

    public String idReading;
    public String title;
    public String author;
    public String duration;
    public String textReading;
    public Boolean isLiked;
    public Boolean isRead;
    public ArrayList<String> emojis;

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

    static public String getEmijoByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
