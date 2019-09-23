package com.demo.database.objectbox;


import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class User {

    @Id
    public long id;

    public String firstName;

    public String lastName;

    public String email;

    public String mobile;

    public String address;

    public String imageUrl;

//    @Backlink(to = "user")
    public ToMany<Pet> listPets;

}
