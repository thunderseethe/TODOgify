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
    public static final int DATABASE_VERSION = 1;

    public TodoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static abstract class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_TASK = "task";
        public static final String COLUMN_COMPLETED = "completed";
    }
}
