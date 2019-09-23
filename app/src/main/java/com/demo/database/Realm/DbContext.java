package com.demo.database.Realm;

import android.content.Context;


import com.demo.database.room.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class DbContext {
    private Realm realm;

    public DbContext(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
//                .migration(new RealmMigrations())
//                .assetFile("glucosio.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
//        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);

    }

    public Realm getReam() {
        return realm;
    }

    private static DbContext instance;

    public static DbContext getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new DbContext(context);
        }
    }

    public void addUser(final UserObj object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(object);

            }
        });
    }

    public void addAllUser(final List<UserObj> listUsers) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(listUsers);
            }
        });
    }

    public Number getMaxId() {
        return realm.where(UserObj.class).max("id");
    }

    public Number getMaxPetId() {
        return realm.where(Pet.class).max("id");
    }


    public UserObj getUser(String userId) {
        UserObj user = realm.where(UserObj.class).equalTo("id", userId).findFirst();
        return user;
    }

    public List<UserObj> getAllUser() {
        RealmResults<UserObj> dataRealmResults =
                realm.where(UserObj.class).findAll();
        return dataRealmResults;
    }

    public List<UserObj> getUserByPetIds(int petId1, int petId2) {
        RealmResults<UserObj> result = realm.where(UserObj.class).equalTo("listPet.id", petId1).or().equalTo("listPet.id", petId2).sort("id", Sort.DESCENDING).findAll();
        return result;
    }


}