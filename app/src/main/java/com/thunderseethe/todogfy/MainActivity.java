package com.thunderseethe.todogfy;

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

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TODOgify");
        toolbar.setTitleTextColor(0xFFEDE7F6);
        toolbar.setBackgroundColor(0xFFAB47BC);
        setSupportActionBar(toolbar);

        RecyclerView list_view = (RecyclerView) findViewById(R.id.list_view);

        list_view.setLayoutManager(new LinearLayoutManager(this));

        List<Todo> todos = new LinkedList<Todo>();
        todos.add(new Todo("Test 1", true));
        todos.add(new Todo("Test 2", false));

        list_view.setAdapter(new TodoAdapter(todos));
    }
}
