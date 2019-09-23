package com.demo.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.demo.database.room.Migration.MIGRATION_1_2;
import static com.demo.database.room.Migration.MIGRATION_2_3;

@Database(entities = {User.class, Pet.class, UserPetJoin.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;
    private static final String DATABASE_NAME = "wave-database";


    public abstract UserDao userDao();

    public abstract PetDao petDao();
    public abstract UserPetJoinDao userPetJoinDao();

    public synchronized static AppDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return mInstance;
    }


    public static void destroyInstance() {
        mInstance = null;
    }

}