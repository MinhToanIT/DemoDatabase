package com.demo.database.Realm;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;

public class RealmBackupRestore {

    private File EXPORT_REALM_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private String EXPORT_REALM_FILE_NAME = "backup1m.realm";
    private String IMPORT_REALM_FILE_NAME = "default.realm"; // Eventually replace this if you're using a custom db name

    private final static String TAG = RealmBackupRestore.class.getName();

    private Activity activity;
    private Realm realm;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public RealmBackupRestore(Activity activity) {
        this.realm = DbContext.getInstance().getReam();
        this.activity = activity;
    }

    public void backup() {
        // First check if we have storage permissions
        checkStoragePermissions(activity);
        File exportRealmFile;

        Log.d(TAG, "Realm DB Path = " + realm.getPath());

        EXPORT_REALM_PATH.mkdirs();

        // create a backup file
        exportRealmFile = new File(EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME);

        // if backup file already exists, delete it
        exportRealmFile.delete();

        // copy current realm to backup file
        realm.writeCopyTo(exportRealmFile);

        String msg = "File exported to Path: " + EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;
        Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        Log.d(TAG, msg);

//        realm.close();
    }

    public void restore() {
        checkStoragePermissions(activity);
        //Restore
        String restoreFilePath = EXPORT_REALM_PATH + "/" + EXPORT_REALM_FILE_NAME;

        Log.d(TAG, "oldFilePath = " + restoreFilePath);

        copyBundledRealmFile(restoreFilePath, IMPORT_REALM_FILE_NAME);
        Log.d(TAG, "Data restore is done");
    }

    private String copyBundledRealmFile(String oldFilePath, String outFileName) {
        try {
            File file = new File(activity.getApplicationContext().getFilesDir(), outFileName);

            FileOutputStream outputStream = new FileOutputStream(file);

            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
        try {
            File file = new File(activity.getApplicationContext().getFilesDir(), outFileName);
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

    private void checkStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private String dbPath() {
        return realm.getPath();
    }
}