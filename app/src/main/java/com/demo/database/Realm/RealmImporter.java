package com.demo.database.Realm;

import android.app.Activity;

import com.demo.database.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class RealmImporter {
    private Activity activity;
    private String dbaFullName = "default.realm";
    private int rawRealmResource = R.raw.glucosio;

    public RealmImporter(Activity activity) {
        this.activity = activity;
        importData();
    }


    private RealmConfiguration getConfiguration() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(dbaFullName)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                    }
                })
                .build();

        return config;
    }

    public Realm getInstance() {
        Realm realm = Realm.getInstance(getConfiguration());

        return realm;
    }

    //InputStream myInput = context.getAssets().open(dbName);
    public void importData() {
        copyBundledRealmFile(activity.getResources().openRawResource(rawRealmResource), dbaFullName);
        Realm.init(activity);

    }

    private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
        try {
            File file = new File(activity.getFilesDir(), outFileName);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }

            outputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

