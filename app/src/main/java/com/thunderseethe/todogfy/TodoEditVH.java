package com.thunderseethe.todogfy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by dead on 4/9/16.
 */
public class TodoEditVH extends RecyclerView.ViewHolder {

    public final EditText edit_text;
    public final ImageButton delete_btn;

    public TodoEditVH(View itemView) {
        super(itemView);
        edit_text = (EditText) itemView.findViewById(R.id.edit_text);
        delete_btn = (ImageButton) itemView.findViewById(R.id.delete_btn);
    }
}
