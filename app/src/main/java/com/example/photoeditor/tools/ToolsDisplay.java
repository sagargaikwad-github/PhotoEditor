package com.example.photoeditor.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoeditor.R;

import java.util.ArrayList;

public class ToolsDisplay extends AppCompatActivity implements ToolsInterface , View.OnClickListener {
    RecyclerView ToolsDisplay_RV;
    ArrayList<ToolsData> arrayList = new ArrayList<>();
    ToolsAdapter toolsAdapter;
    LinearLayout nextBTN_visible,nextBTN_invisible;
    LinearLayout next_BTN_LL;
    ImageButton imageButton;
    TextView nextBTN_TV;
    ArrayList<String>ToolsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_display);

        All_ids();

        arrayList.add(new ToolsData(R.drawable.ic_brush, "Brush"));
        arrayList.add(new ToolsData(R.drawable.ic_text, "Text"));
        arrayList.add(new ToolsData(R.drawable.filter, "Filter"));
        arrayList.add(new ToolsData(R.drawable.ic_insert_emoticon, "Emoji"));
        arrayList.add(new ToolsData(R.drawable.sticker, "Stickers"));
        arrayList.add(new ToolsData(R.drawable.ic_eraser, "Eraser"));




        ToolsDisplay_RV.setLayoutManager(new GridLayoutManager(ToolsDisplay.this, 2));
        toolsAdapter = new ToolsAdapter(arrayList, ToolsDisplay.this, this);
        ToolsDisplay_RV.setAdapter(toolsAdapter);

    }



    private void All_ids() {
        ToolsDisplay_RV = findViewById(R.id.ToolsDisplay_RV);
        nextBTN_visible = findViewById(R.id.nextBTN_visible);
        nextBTN_invisible = findViewById(R.id.nextBTN_invisible);

        imageButton=findViewById(R.id.imageButton);
        nextBTN_TV=findViewById(R.id.nextBTN_TV);


        imageButton.setOnClickListener(this::onClick);
        nextBTN_TV.setOnClickListener(this::onClick);
    }



    @Override
    public void update_data(ArrayList<String> getList) {

         if (getList.size() > 0) {
           ToolsList=new ArrayList<>();

            nextBTN_invisible.setVisibility(View.GONE);
            nextBTN_visible.setVisibility(View.VISIBLE);
            ToolsList=getList;
        } else {
            nextBTN_visible.setVisibility(View.GONE);
            nextBTN_invisible.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        if(view==imageButton || view==nextBTN_TV)
        {
            Intent intent=new Intent(ToolsDisplay.this, AddPhoto.class);
            intent.putExtra("ToolsList", ToolsList);
            startActivity(intent);

        }

    }
}