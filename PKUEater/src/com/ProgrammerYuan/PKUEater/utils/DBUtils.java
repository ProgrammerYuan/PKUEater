package com.ProgrammerYuan.PKUEater.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import studio.archangel.toolkitv2.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by shonenight on 2015/5/13.
 */
public class DBUtils {
    static SQLiteDatabase db;

    static SQLiteDatabase getDb(Context context) {
        if (db == null) {
            File file = new File(context.getFilesDir(), "cities.db");
            if (file.exists()) {
                db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
                return db;
            } else {
                copyDatabase(context, "cities.db");
                file = new File(context.getFilesDir(), "cities.db");
                if (file.exists()) {
                    db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
                    return db;
                } else {
                    return null;
                }
            }
        } else {
            return db;
        }
    }

    public static void releaseDb() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    static void copyDatabase(Context context, String dbName) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            File file = new File(context.getFilesDir(), dbName);
            if (file != null && !file.exists()){
                Logger.out("start copy database...");
                is = context.getAssets().open(dbName);
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024*8];
                int len = -1;
                while ((len = is.read(buffer)) != -1){
                    fos.write(buffer, 0, len);
                }
                Logger.out("end copy database...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<String> getAcademy(Context context, int school_id, String keyword) {
        Cursor cursor = getDb(context).query("academy", new String[]{"name"}, "`schoolid`=" + school_id + " and `name` like '%" + keyword + "%'", null, null, null, null, null);
        ArrayList<String> academies = new ArrayList<>();
        int rows = cursor.getCount();
        if (rows > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < rows; i++) {
                String academy = cursor.getString(cursor.getColumnIndex("name"));
                academies.add(academy);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return academies;
    }

    public static  ArrayList<ContentValues> getRegionList(Context context, int type, int parentID){
        ArrayList<ContentValues> regionList = new ArrayList<ContentValues>();
        Cursor cursor;
        if (type == 1){
            cursor = getDb(context).query("city", null, "leveltype=1", null, null, null, null, null);
        }else if (type == 2){
            cursor = getDb(context).query("city", null, "leveltype=2 and parentid="+parentID, null, null, null, null, null);
        }else{
            cursor = getDb(context).query("city", null, "leveltype=2", null, null, null, null, null);
        }

        while(cursor.moveToNext()) {
            ContentValues map = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(cursor, map);
            regionList.add(map);

        }
        return regionList;
    }
}