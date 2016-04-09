package com.thunderseethe.todogfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView list_view = (RecyclerView) findViewById(R.id.list_view);

        list_view.setLayoutManager(new LinearLayoutManager(this));

        List<Todo> todos = new LinkedList<Todo>();
        todos.add(new Todo("Test 1", true));
        todos.add(new Todo("Test 2", false));

        list_view.setAdapter(new TodoAdapter(todos));
    }
}
