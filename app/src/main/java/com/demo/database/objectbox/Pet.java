package com.demo.database.objectbox;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Pet {
    @Id
    public long id;

    public String name;
    public String field1;
    public String field2;
    public String field3;
    public String field4;
    public String field5;
    public String field6;
    public String field7;
    public String field8;

//    ToOne<User> user;

    public Pet() {
    }

    public Pet(String name, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
        this.name = name;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
    }

}
