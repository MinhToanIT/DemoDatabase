package com.demo.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserPetJoinDao {
    @Insert(onConflict = REPLACE)
    void insert(UserPetJoin userPetJoin);

    @Query("SELECT * FROM user INNER JOIN user_pet_join ON" +
            "            user.id= user_pet_join.userId WHERE" +
            "            user_pet_join.petId=:petId1 OR user_pet_join.petId=:petId2" +
//            "            GROUP BY user_pet_join.userId HAVING COUNT(user_pet_join.userId)>1" +
            "            ORDER BY user_pet_join.userId DESC")
//    @Query("SELECT * FROM user  INNER JOIN user_pet_join ON user.Id = user_pet_join.userId INNER JOIN pet ON user_pet_join.petId = pet.id where pet.id = :petId1 or pet.id = :petId2")
//    @Query("SELECT * FROM user where user.id in (select user_pet_join.userId from user_pet_join where user_pet_join.petId = :petId1 or user_pet_join.petId = :petId2 )")
    List<User> getUsersForPetIds(final int petId1, final int petId2);

    @Query("SELECT * FROM pet INNER JOIN user_pet_join ON\n" +
            "            pet.id = user_pet_join.petId WHERE\n" +
            "            user_pet_join.userId =:userId")
    List<Pet> getPetForUser(final int userId);

    @Query("select user_pet_join.petId from user_pet_join where user_pet_join.userId = :userId")
    List<Integer> getPetIdsByUser(int userId);
}