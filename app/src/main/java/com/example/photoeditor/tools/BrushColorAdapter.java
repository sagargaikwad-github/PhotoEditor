package com.example.photoeditor.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class BrushColorAdapter extends RecyclerView.Adapter<BrushColorAdapter.holder> {
    ArrayList<Integer>arrayList;
    Context context;
    BottomSheetDialog bottomSheetDialog;
    BrushColor brushColor;

    public BrushColorAdapter(ArrayList<Integer> arrayList, Context context, BottomSheetDialog bottomSheetDialog, BrushColor brushColor) {
        this.arrayList = arrayList;
        this.context = context;
        this.bottomSheetDialog = bottomSheetDialog;
        this.brushColor = brushColor;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.color_picker_item_list,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {
        holder.color_picker_view.setBackgroundColor(ContextCompat.getColor(context,arrayList.get(position)));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                brushColor.BrushColor(ContextCompat.getColor(context,arrayList.get(position)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class holder extends RecyclerView.ViewHolder {
        View color_picker_view;
        public holder(@NonNull View itemView) {
            super(itemView);

            color_picker_view=itemView.findViewById(R.id.color_picker_view);
        }
    }
}
