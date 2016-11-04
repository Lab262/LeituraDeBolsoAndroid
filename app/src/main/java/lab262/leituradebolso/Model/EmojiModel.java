package lab262.leituradebolso.Model;

import io.realm.RealmObject;

/**
 * Created by luisresende on 28/09/16.
 */

public class EmojiModel extends RealmObject {
    public String code;

    public EmojiModel(){

    }

    public EmojiModel (String code){
        this.code = code;
    }

    public String getEmojiByUnicode(){
        return new String(Character.toChars(Integer.parseInt(this.code)));
    }
}
