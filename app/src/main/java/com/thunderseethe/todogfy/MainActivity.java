package com.thunderseethe.todogfy;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(0xFF000000);
        toolbar.setBackgroundColor(0xFFFFA500);
        setSupportActionBar(toolbar);

        list_view = (RecyclerView) findViewById(R.id.list_view);

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

    public List<Todo> filter(List<Todo> oldList){
        List<Todo> newList = new LinkedList<Todo>();

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
                Intent edit_intent = new Intent();
                //Edit activity intent here
                return true;
            case R.id.action_clear:
                adapter = new TodoAdapter(filter(adapter.content));
                list_view.setAdapter(adapter);

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
