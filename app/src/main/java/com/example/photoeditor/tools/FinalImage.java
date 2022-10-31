package com.example.photoeditor.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.photoeditor.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalImage extends AppCompatActivity {

    ImageView imageView;
    Button home,share;
    Uri uri ;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_image);

        imageView=findViewById(R.id.imageView2);
        home=findViewById(R.id.homeBTN);
        share=findViewById(R.id.shareBTN);

        Bundle b=getIntent().getExtras();

       try {
         uri=b.getParcelable("final");
        // bitmap=b.getParcelable("final");
       }catch (Exception e)
       {
       }

//    if(uri.equals(null))
//    {
//        imageView.setImageBitmap(bitmap);
//    }
//    else
//    {
        imageView.setImageURI(uri);
   // }



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FinalImage.this,ToolsDisplay.class);
                startActivity(intent);
               finishAffinity();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Share image using"));


            }
        });

    }

    @Override
    public void onBackPressed() {
      this.finish();
      super.onBackPressed();

    }
}