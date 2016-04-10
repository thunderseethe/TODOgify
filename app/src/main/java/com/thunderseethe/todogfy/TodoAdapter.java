package com.thunderseethe.todogfy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

//https://github.com/thunderseethe/TODOgify.git

public class TodoAdapter extends RecyclerView.Adapter<TodoVH> {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    public final List<Todo> content;


    //private int choice = 0;
    //private String strName = "0";
    //private String tempString = "";


    public TodoAdapter(List<Todo> _content){
        this.content = _content;
    }

    @Override
    public TodoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if(viewType == ITEM)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_todo_item, parent, false);

        return new TodoVH(v, viewType);
    }

    public void bindItem(final TodoVH holder, final int position) {
        final TodoAdapter adapter = this;
        final Todo item = this.content.get(position);

        Log.d("Priority", String.format("%d %s", item.priority, new String(new char[item.priority]).replace('\0', '!')));

        holder.text_view.setText(item.task);
        holder.priority_view.setText(new String(new char[item.priority]).replace('\0', '!'));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.content.set(position, item.complete(!item.complete));
                adapter.notifyItemChanged(position);
            }
        });

        ((TodoCardView)holder.root).strikethrough(item.complete);
        if(item.complete) {
            holder.root.setBackgroundColor(0xFFE0E0E0);
            holder.text_view.setTextColor(0xFFAAAAAA);
            holder.priority_view.setTextColor(0xFFAAAAAA);
        } else {
            holder.root.setBackgroundColor(0xFFFFFFFF);
            holder.text_view.setTextColor(0xFF000000);
            holder.priority_view.setTextColor(0xFF30426B);
        }
    }

    public void bindFooter(final TodoVH holder, final List<Todo> content) {
        //Empty for now
        final TodoAdapter adapter = this;
        holder.edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                final String task = holder.edit_text.getText().toString();

                adapter.notifyItemInserted(adapter.content.size());
                adapter.content.add(new Todo(task, false, 2));

                holder.edit_text.setText("");
                holder.edit_text.requestFocus();
                return true;
            }
        });
    }

    @Override
    public void onBindViewHolder(final TodoVH holder, final int position) {
        switch (holder.type) {
            case ITEM:
                bindItem(holder, position);
                break;
            case FOOTER:
                bindFooter(holder, this.content);
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position >= this.content.size()) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return this.content.size() + 1;
    }
}
