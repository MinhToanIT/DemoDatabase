package com.demo.database;

import android.app.Application;

import com.demo.database.objectbox.MyObjectBox;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {
    private static App sApp;
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();

        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                RealmInspectorModulesProvider.builder(this)
                                        .withDeleteIfMigrationNeeded(true) //if there is any changes in database schema then rebuild bd.
                                        .withMetaTables() //extract table meta data
                                        .withLimit(10000) //by default limit of data id 250, but you can increase with this
                                        .build()
                        )
                        .build());
    }

    public static App getApp() {
        return sApp;
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
