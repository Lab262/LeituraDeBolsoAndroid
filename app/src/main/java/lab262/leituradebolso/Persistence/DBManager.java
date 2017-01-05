package lab262.leituradebolso.Persistence;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Model.UserReadingModel;

/**
 * Created by luisresende on 28/09/16.
 */

public class DBManager {

    private static String keyFirstTimeApp = "keyFirstTimeApp";

    public static void saveObject(RealmObject object){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
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

    public static RealmResults<? extends RealmObject> getAllByParameter(Class<? extends RealmObject> className,
                                                                        String nameParameter, Boolean parameter){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<? extends RealmObject> resultGetAll = realm.where(className).equalTo(nameParameter,parameter).findAll();
        return resultGetAll;
    }

    public static RealmResults<? extends RealmObject> getAllByParameter(Class<? extends RealmObject> className,
                                                                        String nameParameter, ArrayList<String> parameters){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<? extends RealmObject> resultGetAll = realm.where(className);
        int i = 0;
        for(String id : parameters) {
            if(i != 0) {
                resultGetAll = resultGetAll.or();
            }
            resultGetAll = resultGetAll.equalTo(nameParameter, id);
            i++;
        }
        return resultGetAll.findAll();
    }

    public static UserModel getCachedUser(){
        RealmResults<UserModel> realmResults = (RealmResults<UserModel>) getAll(UserModel.class);
        UserModel user;
        if (realmResults.size()>0){
            user = realmResults.first();
        }else {
            user = null;
        }

        return user;
    }

    public static UserReadingModel getUserReadingModelByID(String idReading){
        RealmResults<UserReadingModel> realmResults = (RealmResults<UserReadingModel>)
                DBManager.getAllByParameter(UserReadingModel.class,UserReadingModel.nameParameterIDReading,idReading);
        return realmResults.first();
    }

    public static void deleteDatabase(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public static Boolean isFirstTime(Context context){

        SharedPreferences preferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int sizePreferences = preferences.getAll().size();

        if (sizePreferences == 0) {
            editor.putBoolean(keyFirstTimeApp, true);
            editor.apply();
            return true;
        }
        return false;
    }

}
