package com.example.photoeditor.tools;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.R;

import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.holder> {
    ArrayList<Integer>StickerList;
    Context context;
    StickerInterface stickerInterface;

    public StickerAdapter(ArrayList<Integer> stickerList, Context context, StickerInterface stickerInterface) {
        StickerList = stickerList;
        this.context = context;
        this.stickerInterface = stickerInterface;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {


        //holder.emojiTV.setText(String.valueOf(Character.toChars(0x1F601)));
        holder.stickerIV.setImageResource(StickerList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               stickerInterface.sticker(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return StickerList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView stickerIV;
        public holder(@NonNull View itemView) {
            super(itemView);

            stickerIV=itemView.findViewById(R.id.stickerIV);



        }
    }
}
