package lab262.leituradebolso.Persistence;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import lab262.leituradebolso.Model.UserModel;

/**
 * Created by luisresende on 28/09/16.
 */

public class DBManager {

    public static void addObject(RealmObject object){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    public static RealmResults<? extends RealmObject> getAll(Class<? extends RealmObject> className){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<? extends RealmObject> resultGetAll = realm.where(className).findAll();
        return resultGetAll;
    }

    public static RealmResults<? extends RealmObject> getAllByParameter(Class<? extends RealmObject> className,
                                                                        String nameParameter, String parameter){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<? extends RealmObject> resultGetAll = realm.where(className).equalTo(nameParameter,parameter).findAll();
        return resultGetAll;
    }

    public static UserModel getCachedUser(){
        RealmResults<UserModel> realmResults = (RealmResults<UserModel>) getAll(UserModel.class);
        UserModel user = realmResults.first();
        return user;
    }

}
