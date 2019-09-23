package com.demo.database.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(User mUser);

    @Insert
    void insertAllUser(List<User> listUser);

    @Delete
    void delete(User mUser);

    @Update
    void updateUser(User mUser);

    @Query("SELECT * FROM user WHERE id = :uId")
    User getUserById(int uId);


    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);
//    @Query("SELECT * FROM user,pet WHERE user.id= pet IN (:userIds)")
//    List<User> getUserByPetIds(int petId1, int petId2);

//    @Query("SELECT * FROM user WHERE firstName LIKE :first AND " +
//            "last_update LIKE :last LIMIT 1")
//    UserObj findByName(String first, String last);


}