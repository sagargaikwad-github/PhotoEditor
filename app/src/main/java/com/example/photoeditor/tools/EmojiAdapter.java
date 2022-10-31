package com.example.photoeditor.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.R;

import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.holder> {
    ArrayList<Integer>EmojiList;
    Context context;
    EmojiInterface emojiInterface;

    public EmojiAdapter(ArrayList<Integer> emojiList, Context context, EmojiInterface emojiInterface) {
        EmojiList = emojiList;
        this.context = context;
        this.emojiInterface = emojiInterface;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {


        //holder.emojiTV.setText(String.valueOf(Character.toChars(0x1F601)));
       holder.emojiTV.setText(String.valueOf(Character.toChars(EmojiList.get(position))));

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               emojiInterface.Emoji(position);
           }
       });

    }

    @Override
    public int getItemCount() {
        return EmojiList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView emojiTV;
        public holder(@NonNull View itemView) {
            super(itemView);

            emojiTV=itemView.findViewById(R.id.emojiTV);



        }
    }
}
