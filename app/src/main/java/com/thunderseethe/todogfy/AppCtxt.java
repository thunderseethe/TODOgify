package com.thunderseethe.todogfy;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dead on 4/10/16.
 */
public class AppCtxt extends Application {

    private MainActivity activity;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    /*public List<Todo> pullTodos() {
        SQLiteDatabase db = new TodoDB(this).getReadableDatabase();
        Cursor c = db.query(TodoDB.TodoEntry.TABLE_NAME, null, null, null, null, null, null);
        List<Todo> todos = new LinkedList<>();

        if(c.getCount() == 0) return todos;
        c.moveToFirst();

        do {
            int id = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_ID));
            String task  = c.getString(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_TASK));
            boolean complete = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_COMPLETED)) != 0;
            todos.add(new Todo(id, task, complete));
        } while(c.moveToNext());

        c.close();

        return todos;
    }*/


}
