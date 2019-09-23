package com.demo.database.Realm;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserObj extends RealmObject {
    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String address;

    public UserObj() {
    }

    public UserObj(int id, String firstName, String lastName, String email, String mobile, String address, RealmList<Pet> listPet) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.listPet = listPet;
    }

    private RealmList<Pet> listPet;

    public RealmList<Pet> getListPet() {
        return listPet;
    }

    public void setListPet(RealmList<Pet> listPet) {
        this.listPet = listPet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
