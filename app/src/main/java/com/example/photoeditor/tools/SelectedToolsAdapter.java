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

public class SelectedToolsAdapter extends RecyclerView.Adapter<SelectedToolsAdapter.viewHolder> {
    ArrayList<String>arrayList;
    Context context;

   ToolsClick toolsClick;

    public SelectedToolsAdapter(ArrayList<String> arrayList, Context context, ToolsClick toolsClick) {
        this.arrayList = arrayList;
        this.context = context;
        this.toolsClick = toolsClick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_editing_tools, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {


            holder.txtTool.setText(arrayList.get(position));


       switch (arrayList.get(position))
       {
           case "Brush":
               holder.imgToolIcon.setImageResource(R.drawable.ic_brush);
               arrayList.add("Eraser");
               break;
           case "Text":
               holder.imgToolIcon.setImageResource(R.drawable.ic_text);
               break;
           case "Filter":
               holder.imgToolIcon.setImageResource(R.drawable.filter);
               break;
           case "Emoji":
               holder.imgToolIcon.setImageResource(R.drawable.ic_insert_emoticon);
               break;
           case "Stickers":
               holder.imgToolIcon.setImageResource(R.drawable.sticker);
               break;
           case "Eraser":
               holder.imgToolIcon.setImageResource(R.drawable.ic_eraser);
               break;
       }

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onClick(View view) {
               toolsClick.toolsClick(arrayList.get(position));
           }
       });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imgToolIcon;
        TextView txtTool;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgToolIcon=itemView.findViewById(R.id.imgToolIcon);
            txtTool=itemView.findViewById(R.id.txtTool);
        }
    }
}
