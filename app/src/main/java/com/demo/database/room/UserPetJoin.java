package com.demo.database.room;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "user_pet_join",
        primaryKeys = { "userId", "petId" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"),
                @ForeignKey(entity = Pet.class,
                        parentColumns = "id",
                        childColumns = "petId")
        })
public class UserPetJoin {
    public final int userId;
    public final int petId;

    public UserPetJoin(final int userId, final int petId) {
        this.userId = userId;
        this.petId = petId;
    }
}