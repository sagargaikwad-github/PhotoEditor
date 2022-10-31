package com.example.photoeditor.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoeditor.R;

import java.util.ArrayList;


public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.viewHolder> {
    ArrayList<ToolsData> arrayList;
    Context context;
    ArrayList<String> datalist = new ArrayList<>();
    ToolsInterface toolsInterface;


    public ToolsAdapter(ArrayList<ToolsData> arrayList, Context context, ToolsInterface toolsInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.toolsInterface = toolsInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tools_display_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(arrayList.get(position).getTool_name()=="Eraser")
        {
            arrayList.remove(position);

        }else
        {
            holder.tools_display_item_TV.setText(arrayList.get(position).getTool_name());
            holder.tools_display_item_IV.setImageResource(arrayList.get(position).getTool_icon());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datalist != null) {
                    if (datalist.contains(arrayList.get(position).getTool_name())) {
                        holder.itemView.setBackgroundColor(Color.parseColor("#0D000000"));
                        datalist.remove(arrayList.get(position).getTool_name());

                    } else {
                        holder.itemView.setBackgroundColor(Color.parseColor("#fff3b6"));
                        datalist.add(arrayList.get(position).getTool_name());
                    }
                    toolsInterface.update_data(datalist);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView tools_display_item_IV;
        TextView tools_display_item_TV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tools_display_item_IV = itemView.findViewById(R.id.tools_display_item_IV);
            tools_display_item_TV = itemView.findViewById(R.id.tools_display_item_TV);
        }
    }

}
