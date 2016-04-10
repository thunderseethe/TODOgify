package com.thunderseethe.todogfy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter adapter;
    private RecyclerView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TodoDB fix_db = new TodoDB(this);
        fix_db.onUpgrade(fix_db.getWritableDatabase(), 1, 2);*/
        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(0xFF000000);
        toolbar.setBackgroundColor(0xFF30729B);
        setSupportActionBar(toolbar);

        // Setup notification bar
        /*Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(0xFF30729B);*/

        // Setup recycle view
        list_view = (RecyclerView) findViewById(R.id.list_view);
        list_view.setLayoutManager(new LinearLayoutManager(this));
        List<Todo> todos = pullTodos();


        // Setup adapter
        startService(todos);
        adapter = new TodoAdapter(todos);
        list_view.setAdapter(adapter);
    }

    public void startService(List<Todo> todos) {
        if(todos.isEmpty()) return;
        Intent service = new Intent(this, NotificationService.class);
        service.putExtra("todo", Collections.max(todos));
        startService(service);
    }

    public List<Todo> pullTodos() {
        SQLiteDatabase db = new TodoDB(this).getReadableDatabase();
        Cursor c = db.query(TodoDB.TodoEntry.TABLE_NAME, null, null, null, null, null, null);
        List<Todo> todos = new LinkedList<>();

        if(c.getCount() == 0) return todos;
        c.moveToFirst();

        do {
            int id = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_ID));
            String task  = c.getString(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_TASK));
            boolean complete = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_COMPLETED)) != 0;
            int priority = c.getInt(c.getColumnIndex(TodoDB.TodoEntry.COLUMN_PRIORITY));

            todos.add(new Todo(id, task, complete, priority));
        } while(c.moveToNext());

        c.close();

        return todos;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SQLiteDatabase db = new TodoDB(this).getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();
        db.execSQL("DELETE FROM " + TodoDB.TodoEntry.TABLE_NAME + ";");

        for(Todo todo : adapter.content) {
            if(todo.id != -1)
                values.put(TodoDB.TodoEntry.COLUMN_ID, todo.id);

            values.put(TodoDB.TodoEntry.COLUMN_TASK, todo.task);
            values.put(TodoDB.TodoEntry.COLUMN_COMPLETED, todo.complete ? 1 : 0);
            values.put(TodoDB.TodoEntry.COLUMN_PRIORITY, todo.priority);

            db.insert(TodoDB.TodoEntry.TABLE_NAME, null, values);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    public List<Todo> filterNotCompleted(List<Todo> oldList){
        List<Todo> newList = new LinkedList<>();

        for (Todo todo : oldList) {
            if(!todo.complete)
                newList.add(todo);
        }

        return newList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit:
                Intent edit_intent = new Intent(this, EditActivity.class);
                edit_intent.putParcelableArrayListExtra("todos", new ArrayList<>(adapter.content));
                startActivityForResult(edit_intent, 0);
                return true;
            case R.id.action_clear:
                adapter = new TodoAdapter(filterNotCompleted(adapter.content));
                list_view.setAdapter(adapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;

        List<Todo> todos = data.getParcelableArrayListExtra("todos");
        adapter = new TodoAdapter(todos);
        list_view.setAdapter(adapter);
    }
}
