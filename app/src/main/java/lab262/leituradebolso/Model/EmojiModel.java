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

    public String getEmijoByUnicode(){
        //TODO: Arrumar codigos dos emojis
        String ws = "128513";
        int emojis = 0x1F601;

        String emoji = new String(Character.toChars(Integer.parseInt(ws)));
        return emoji;
    }
}
