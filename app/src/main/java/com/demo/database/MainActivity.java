package com.demo.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.demo.database.Realm.DbContext;
import com.demo.database.Realm.Pet;
import com.demo.database.Realm.UserObj;
import com.demo.database.objectbox.Pet_;
import com.demo.database.objectbox.User_;
import com.demo.database.room.AppDatabase;
import com.demo.database.room.User;
import com.demo.database.room.UserPetJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    //Test ////
    private ProgressDialog progressDialog;
    private DbContext dbContext;
    private AppDatabase appDatabase;
    private BoxStore boxStore;
    private Box<com.demo.database.objectbox.User> userBox;

    private Random random = new Random();
    List<Pet> listPetsRealm = new ArrayList<>();
    List<com.demo.database.room.Pet> listPetsRoom = new ArrayList<>();
    List<com.demo.database.objectbox.Pet> listPetObjectBox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        appDatabase = AppDatabase.getDatabaseInstance(this);
        boxStore = App.getApp().getBoxStore();

        initRealmData();
//        insertRealmData();

//        RealmBackupRestore realmBackupRestore  = new RealmBackupRestore(this);
//        realmBackupRestore.backup();
//        realmBackupRestore.restore();

//        RealmImporter realmImporter = new RealmImporter(this);
//        realmImporter.importData();

//        getAllUsersFromRealm();
//        getUserRealmByPetIds(random.nextInt(listPetsRealm.size()), random.nextInt(listPetsRealm.size()));
        getUserRealmByPetIds(1, 8);
//
//        insertRoomData();
//        try {
//            FileUtil.copyDataBase(this,"wave-database");
//            FileUtil.copyDataBase(this,"wave-database-shm");
//            FileUtil.copyDataBase(this,"wave-database-wal");
//        getRoomData();
//        getUserRoomByPetIds(random.nextInt(listPetsRoom.size()), random.nextInt(listPetsRoom.size()));
//        getUserRoomByPetIds(15, 33);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        initObjectBoxData();
//        getObjectBoxData();
//        getUserObjBoxByPetIds(18, 47);

        progressDialog.dismiss();

    }

    private void initRealmData() {
        DbContext.init(this);
        dbContext = DbContext.getInstance();
    }

    private void insertRealmData() {
        List<UserObj> listUsers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Pet pet = new Pet(i + 1, "pet" + (i + 1), "field1", "field2", "field2", "field4", "field5", "field6", "field7", "field8");
            listPetsRealm.add(pet);
        }

        Number maxId = dbContext.getMaxId();
        Number maxPetId = dbContext.getMaxPetId();
        for (int i = 0; i < 100000; i++) {
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
            UserObj user = new UserObj();
            user.setId(nextId);
            user.setFirstName("Kevin");
            user.setLastName("Hicom");
            user.setEmail("kevin@gmail.com");
            user.setAddress("Melbourne");
            user.setMobile("0838573483");

            RealmList<Pet> listPet = new RealmList<>();
            for (int j = 0; j < 10; j++) {
//                int nextPetId = (maxPetId == null) ? 1 : maxPetId.intValue() + 1;
//                Pet pet = new Pet(nextPetId, "pet" + (j + 1), "field1", "field2", "field2", "field4", "field5", "field6", "field7", "field8");
                listPet.add(listPetsRealm.get(random.nextInt(listPetsRealm.size())));
//                maxPetId = nextPetId;
//
            }
            user.setListPet(listPet);
            listUsers.add(user);
            maxId = nextId;

        }

        long startTime = System.currentTimeMillis();
        dbContext.addAllUser(listUsers);
        long endTime = System.currentTimeMillis();
        Log.e("time insert", "realm: " + (endTime - startTime));

    }

    private void getAllUsersFromRealm() {
        long startTime = System.currentTimeMillis();
        List<UserObj> listUser = dbContext.getAllUser();
        long endTime = System.currentTimeMillis();
        Log.e("time query", "realm:" + (endTime - startTime));
        Log.e("kevin", "maxId: " + dbContext.getMaxId());
//        for (UserObj userObj : listUser) {
//            Log.e("kevin", "id: " + userObj.getId());
//            Log.e("kevin", "pet size: " + userObj.getListPet().size());
//            for (Pet pet : userObj.getListPet()) {
//                Log.e("kevin", "pet id: " + pet.getId() + "- Pet name:" + pet.getName());
//            }
//        }
        Log.e("kevin", "user size: " + listUser.size());
        Log.e("kevin", "pet size: " + listUser.size());
    }

    private void getUserRealmByPetIds(int petId1, int petId2) {
        long startTime = 0, endTime = 0;
        for (int i = 0; i < 1000; i++) {
            startTime += System.currentTimeMillis();
            List<UserObj> listUser = dbContext.getUserByPetIds(petId1, petId2);
            endTime += System.currentTimeMillis();
        }
        Log.e("time query avg", "realm: " + (endTime - startTime) / 1000F);

//        Log.e("kevin", "ids: " + petId1 + "," + petId2);
//        Log.e("kevin", "user by pet ids size: " + listUser.size());
    }

    private void insertRoomData() {
        List<User> listUser = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setFirstName("Kevin");
            user.setLastName("Hicom");
            user.setEmail("kevin@gmail.com");
            user.setAddress("Melbourne");
            user.setMobile("0838573483");
            appDatabase.userDao().insertUser(user);
            listUser.add(user);
        }

//        appDatabase.userDao().insertAllUser(listUser);

        for (int i = 0; i < 100; i++) {
            com.demo.database.room.Pet pet = new com.demo.database.room.Pet(i + 1, "pet" + (i + 1), "field1", "field2", "field2", "field4", "field5", "field6", "field7", "field8");
            listPetsRoom.add(pet);
            appDatabase.petDao().insertPet(pet);
        }
//        appDatabase.petDao().insertAllPet(listPetsRealm);

        for (int i = 0; i < listUser.size(); i++) {
            for (int j = 0; j < 10; j++) {
                appDatabase.userPetJoinDao().insert(new UserPetJoin(i + 1, random.nextInt(listPetsRoom.size()) + 1));
            }
        }


    }

    private void getRoomData() {
        long startTime = System.currentTimeMillis();
        List<User> listUsers = appDatabase.userDao().getAll();
        long endTime = System.currentTimeMillis();
        Log.e("time query", "room:" + (endTime - startTime));

        Log.e("room", "users size: " + listUsers.size());

        getUserWithPetIds(listUsers);

//        long startTime1 = System.currentTimeMillis();
//        Log.e("room", "pets size: " + appDatabase.petDao().getAllPet().size());
//        long endTime1 = System.currentTimeMillis();
//        Log.e("time query", "room:" + (endTime1 - startTime1));
    }

    private void getUserRoomByPetIds(int petId1, int petId2) {
        long startTime = 0, endTime = 0;
        for (int i = 0; i < 1000; i++) {
            startTime += System.currentTimeMillis();
            List<User> listUser = appDatabase.userPetJoinDao().getUsersForPetIds(petId1, petId2);
            endTime += System.currentTimeMillis();
        }
        Log.e("time query avg", "room: " + (endTime - startTime) / 1000F);

//        Log.e("kevin", "objbox ids: " + petId1 + "," + petId2);
//        Log.e("kevin", "room user by pet Ids size: " + listUser.size());
//
//        for (User user : listUser) {
//            Log.e("kevin", "userId: " + user.getId());
//        }
    }

    private List<Integer> getPetIdsByUser(int userId) {
        List<Integer> listPetIds = appDatabase.userPetJoinDao().getPetIdsByUser(userId);
        return listPetIds;
    }

    private void getUserWithPetIds(List<User> listUser) {
        for (User user : listUser) {
            String petIds = "";
            for (int i : appDatabase.userPetJoinDao().getPetIdsByUser(user.getId())) {
                petIds += i + ",";
            }
            Log.e("kevin", user.getId() + ":" + petIds);
        }

    }

    private void initObjectBoxData() {
        userBox = boxStore.boxFor(com.demo.database.objectbox.User.class);

        List<com.demo.database.objectbox.User> listUser = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            com.demo.database.objectbox.Pet pet = new com.demo.database.objectbox.Pet("pet" + (i + 1), "field1", "field2", "field2", "field4", "field5", "field6", "field7", "field8");
            listPetObjectBox.add(pet);
        }
        for (int i = 0; i < 100000; i++) {

            com.demo.database.objectbox.User user = new com.demo.database.objectbox.User();
            user.firstName = "Kevin";
            user.lastName = "Hicom";
            user.email = "kevin@gmail.com";
            user.address = "Melbourne";
            user.mobile = "0838573483";

            for (int j = 0; j < 10; j++) {
                user.listPets.add(listPetObjectBox.get(random.nextInt(listPetObjectBox.size())));
            }
            listUser.add(user);

        }

//        long startTime = System.currentTimeMillis();
        userBox.put(listUser);
//        long endTime = System.currentTimeMillis();
//        Log.e("time insert", "objectBox: " + (endTime - startTime));

    }

    private void getObjectBoxData() {
        userBox = boxStore.boxFor(com.demo.database.objectbox.User.class);
        long startTime = System.currentTimeMillis();
        List<com.demo.database.objectbox.User> listUser = userBox.getAll();
        long endTime = System.currentTimeMillis();
        Log.e("time query", "objectBox: " + (endTime - startTime));
        Log.e("objectbox", "user size: " + listUser.size());

        for (com.demo.database.objectbox.User user : listUser) {

            String petIds = "";
            for (com.demo.database.objectbox.Pet pet : user.listPets) {
                petIds += pet.id + ",";
            }
            Log.e("toan", "petIds: " + petIds);
        }
    }

    private void getUserObjBoxByPetIds(long petId1, final long petId2) {
        userBox = boxStore.boxFor(com.demo.database.objectbox.User.class);

        long startTime = 0, endTime = 0;
        for (int i = 0; i < 1000; i++) {
            startTime += System.currentTimeMillis();
            QueryBuilder<com.demo.database.objectbox.User> builder = userBox.query();
            builder.link(User_.listPets).equal(Pet_.id, petId1).or().equal(Pet_.id, petId2);
            List<com.demo.database.objectbox.User> listUsers = builder.order(User_.id, QueryBuilder.DESCENDING).build().find();
            endTime += System.currentTimeMillis();
        }

        Log.e("time query avg", "objectbox: " + (endTime - startTime) / 1000F);

//        Log.e("kevin", "objbox ids: " + petId1 + "," + petId2);
//        Log.e("kevin", "objectBox: user by pet ids size: " + listUsers.size());
//
//        for (com.demo.database.objectbox.User user : listUsers) {
//
//            String petIds = "";
//            for (com.demo.database.objectbox.Pet pet : user.listPetsRealm) {
//                petIds += pet.id + ",";
//            }
//            Log.e("kevin", "petIds: " + petIds);
//        }
    }

}
