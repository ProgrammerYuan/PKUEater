package com.ProgrammerYuan.PKUEater.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.text.TextUtils;
import com.ProgrammerYuan.PKUEater.model.Canteen;
import com.ProgrammerYuan.PKUEater.model.DBEntry;
import com.ProgrammerYuan.PKUEater.model.Dish;
import studio.archangel.toolkitv2.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michael on 2014/10/21.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static String database_name = "PKUEater";
    // Database creation sql statement
    public static ArrayList<String> database_create = new ArrayList<String>();//"create table MyEmployees( _id integer primary key,name text not null);";
    public static ArrayList<Class<? extends DBEntry>> entry_tables;
    public static int database_version = 1;

    public DBHelper(Context context) {
        super(context, database_name, new SQLiteCursorFactory(true), database_version);
        init();
    }

    public static void init() {
        entry_tables = new ArrayList<Class<? extends DBEntry>>();
        entry_tables.add(Canteen.class);
        entry_tables.add(Dish.class);
        for (Class<? extends DBEntry> cl : entry_tables) {
            try {
                database_create.add(cl.newInstance().getCreatingTableSql());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Logger.out("onOpen");
        Logger.out("db version:" + db.getVersion());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.out("onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.out("onUpgrade oldVersion:" + oldVersion + ",newVersion:" + newVersion);
//        db = getWritableDatabase();
//        for (int i = oldVersion+1; i <= newVersion; i++) {
        upgradeDB(db, oldVersion + 1);
//        }
//        db.close();
//        Log.w(MyDatabaseHelper.class.getName(),
//                "Upgrading database from version " + oldVersion + " to "
//                        + newVersion + ", which will destroy all old data");
//        db.execSQL("DROP TABLE IF EXISTS MyEmployees");
//        onCreate(database);
    }

    void upgradeDB(SQLiteDatabase db, int db_version) {
        Logger.out("upgradeDB db_version:" + db_version);

    }
}

class SQLiteCursorFactory implements SQLiteDatabase.CursorFactory {

    private boolean debugQueries = true;

    public SQLiteCursorFactory() {
        this.debugQueries = false;
    }

    public SQLiteCursorFactory(boolean debugQueries) {
        this.debugQueries = debugQueries;
    }

    @Override
    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery,
                            String editTable, SQLiteQuery query) {
        if (debugQueries) {
//            Log.d("SQL", query.toString());
            Logger.out(query.toString());
        }
        return new SQLiteCursor(db, masterQuery, editTable, query);
    }

}