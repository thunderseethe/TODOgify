package com.thunderseethe.todogfy;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dead on 4/9/16.
 */
public class TodoEditAdapter extends RecyclerView.Adapter<TodoEditVH> {

    public final List<Todo> content;

    public TodoEditAdapter(List<Todo> _content) {
        this.content = _content;
    }

    @Override
    public TodoEditVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.edit_todo_item, parent, false);
        return new TodoEditVH(v);
    }

    @Override
    public void onBindViewHolder(final TodoEditVH holder, final int position) {
        final Todo item = this.content.get(position);
        final TodoEditAdapter adapter = this;

        holder.edit_text.setText(item.task);
        /*holder.edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            content.set(holder.getAdapterPosition(), item.task(textView.getText().toString()));
            return true;
            }
        });*/
        holder.edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                content.set(holder.getAdapterPosition(), item.task(charSequence.toString()));
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            content.remove(holder.getAdapterPosition());
            adapter.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.content.size();
    }
}
