package com.demo.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pet")
    List<Pet> getAllPet();

    @Insert(onConflict = REPLACE)
    void insertPet(Pet pet);

    @Insert
    void insertAllPet(List<Pet> listPet);

    @Delete
    void delete(Pet pet);

    @Update
    void updateUser(Pet pet);

    @Query("SELECT * FROM pet WHERE id = :id")
    Pet getPetById(int id);

//    @Query("Select * from pet where userId= :userId")
//    List<Pet> getAllPetByUserId(int userId);
}
