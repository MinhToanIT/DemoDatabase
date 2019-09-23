package com.demo.database;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static void copyDataBase(Context context, String dbName) throws IOException {

        InputStream myInput = context.getAssets().open(dbName);
        String outFileName =  "/data/data/"
                +context.getApplicationContext().getPackageName()
                + "/databases/" + dbName;
        File file=new File(outFileName);
        if (file.exists())
            return;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
