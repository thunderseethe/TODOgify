package com.thunderseethe.todogfy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by dead on 4/9/16.
 */
public final class TodoDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final int DATABASE_VERSION = 2;

    public TodoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_QUERY = "CREATE TABLE " + TodoEntry.TABLE_NAME + " ( " +
        TodoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        TodoEntry.COLUMN_TASK + " TEXT, " +
        TodoEntry.COLUMN_COMPLETED + " INTEGER, " +
        TodoEntry.COLUMN_PRIORITY + " INTEGER " +
        " )";
    public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {
        sqLiteDatabase.execSQL(DROP_QUERY);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {
        onUpgrade(sqLiteDatabase, old_version, new_version);
    }

    public static abstract class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_PRIORITY  = "priority";
    }
}
