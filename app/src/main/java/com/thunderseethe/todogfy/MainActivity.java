package com.thunderseethe.todogfy;

import android.content.Intent;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(0xFF000000);
        toolbar.setBackgroundColor(0xFFFFA500);
        setSupportActionBar(toolbar);

        RecyclerView list_view = (RecyclerView) findViewById(R.id.list_view);

        list_view.setLayoutManager(new LinearLayoutManager(this));

        List<Todo> todos = new LinkedList<>();
        todos.add(new Todo("Test 1", true));
        todos.add(new Todo("Test 2", false));

        adapter = new TodoAdapter(todos);
        list_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_edit:
                Intent edit = new Intent(this, EditActivity.class);
                edit.putParcelableArrayListExtra("todos", new ArrayList<>(adapter.content));
                startActivityForResult(edit, 0);
                //Edit activity intent here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}