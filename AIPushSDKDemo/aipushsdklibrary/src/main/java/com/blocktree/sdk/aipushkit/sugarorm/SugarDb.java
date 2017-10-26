package com.blocktree.sdk.aipushkit.sugarorm;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blocktree.sdk.aipushkit.sugarorm.helper.ManifestHelper;
import com.blocktree.sdk.aipushkit.sugarorm.util.SugarCursorFactory;

import static com.blocktree.sdk.aipushkit.sugarorm.SugarContext.getDbConfiguration;
import static com.blocktree.sdk.aipushkit.sugarorm.helper.ManifestHelper.getDatabaseVersion;
import static com.blocktree.sdk.aipushkit.sugarorm.helper.ManifestHelper.getDbName;
import static com.blocktree.sdk.aipushkit.sugarorm.util.ContextUtil.getContext;

public class SugarDb extends SQLiteOpenHelper {
    private static final String LOG_TAG = "Sugar";

    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;
    private int openedConnections = 0;

    //Prevent instantiation
    private SugarDb() {
        super(getContext(), getDbName(), new SugarCursorFactory(ManifestHelper.isDebugEnabled()), getDatabaseVersion());
        schemaGenerator = SchemaGenerator.getInstance();
    }

    public static SugarDb getInstance() {
        return new SugarDb();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        final SugarDbConfiguration configuration = getDbConfiguration();
        if (null != configuration) {
            db.setLocale(configuration.getDatabaseLocale());
            db.setMaximumSize(configuration.getMaxSize());
            db.setPageSize(configuration.getPageSize());
        }
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if(ManifestHelper.isDebugEnabled()) {
            Log.d(LOG_TAG, "getReadableDatabase");
        }
        openedConnections++;
        return super.getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        if(ManifestHelper.isDebugEnabled()) {
            Log.d(LOG_TAG, "getReadableDatabase");
        }
        openedConnections--;
        if(openedConnections == 0) {
            if(ManifestHelper.isDebugEnabled()) {
                Log.d(LOG_TAG, "closing");
            }
            super.close();
        }
    }
}
