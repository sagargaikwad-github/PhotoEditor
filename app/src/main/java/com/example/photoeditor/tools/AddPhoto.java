package com.example.photoeditor.tools;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoeditor.R;
import com.example.photoeditor.filters.FilterListener;
import com.example.photoeditor.filters.FilterViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder;

public class AddPhoto extends AppCompatActivity implements View.OnClickListener,
        ToolsClick,
        FilterListener,
        BrushColor,
        EmojiInterface,
        StickerInterface {
    private static final String IMAGES_FOLDER_NAME = "PhotoEditor Android" ;
    ImageView addPhoto_IV, addIcon_IV;
    TextView addPhoto_TV;
    int CAMERA_REQUEST_CODE = 100;
    int STORAGE_REQUEST_CODE = 200;
    ArrayList<String> arrayList = new ArrayList<>();

    ImageView imgUndo, imgRedo, imgCamera, imgGallery, imgSave, imgClose;
    PhotoEditorView PhotoEditorView;
    PhotoEditor photoEditor;
    RecyclerView rvTools, rvFilters;
    BottomSheetDialog bottomSheetDialog;
    int BrushSize, BrushOptacity, EraserSize;
    ShapeBuilder shapeBuilder;
    EditText enterTextBS;
    int TextColor = Color.parseColor("#000000");
    int Emoji;
    int viewCount=0;
   Uri cameraUri;
   String currentPhotoPath;
    Uri imageUri;

    ArrayList<Integer> colorArray = new ArrayList<>();
    ArrayList<Integer> EmojiList = new ArrayList<>();
    ArrayList<Integer> StickerList = new ArrayList<>();
;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        buttonClicks();

        photoEditor = new PhotoEditor.Builder(this, PhotoEditorView)
                .setPinchTextScalable(true)
                .setClipSourceImage(true)
                .build();

        PhotoEditorView.getSource().setImageResource(R.drawable.paris_tower);

        colorArray.add(R.color.white);
        colorArray.add(R.color.black);
        colorArray.add(R.color.blue_color_picker);
        colorArray.add(R.color.brown_color_picker);
        colorArray.add(R.color.green_color_picker);
        colorArray.add(R.color.red_color_picker);
        colorArray.add(R.color.orange_color_picker);
        colorArray.add(R.color.sky_blue_color_picker);
        colorArray.add(R.color.violet_color_picker);
        colorArray.add(R.color.yellow_color_picker);
        colorArray.add(R.color.yellow_green_color_picker);

        EmojiList.add(0x1F601);
        EmojiList.add(0x1F603);
        EmojiList.add(0x1F604);
        EmojiList.add(0x1F600);
        EmojiList.add(0x1F605);
        EmojiList.add(0x1F606);
        EmojiList.add(0x1F602);
        EmojiList.add(0x1F923);
        EmojiList.add(0x1F602);
        EmojiList.add(0x1F642);
        EmojiList.add(0x1F643);
        EmojiList.add(0x1FAE0);
        EmojiList.add(0x1F609);
        EmojiList.add(0x1F970);
        EmojiList.add(0x1F62A);
        EmojiList.add(0x1F634);
        EmojiList.add(0x1F976);
        EmojiList.add(0x1F97A);
        EmojiList.add(0x1F616);
        EmojiList.add(0x1F624);
        EmojiList.add(0x1F621);
        EmojiList.add(0x1F608);
        EmojiList.add(0x1F921);
        EmojiList.add(0x1F47B);
        EmojiList.add(0x1F63C);
        EmojiList.add(0x1F648);
        EmojiList.add(0x1F649);
        EmojiList.add(0x1F64A);
        EmojiList.add(0x1F496);
        EmojiList.add(0x1F494);
        EmojiList.add(0x1F4AF);
        EmojiList.add(0x1F4A5);
        EmojiList.add(0x1F44B);
        EmojiList.add(0x1F44C);


        StickerList.add(R.drawable.st_butterfly);
        StickerList.add(R.drawable.st_firecracker);
        StickerList.add(R.drawable.st_garland);
        StickerList.add(R.drawable.st_location);
        StickerList.add(R.drawable.st_partyhat);


//         arrayList.add("Brush");
//         arrayList.add("Text");
//         arrayList.add("Filter");
//         arrayList.add("Emoji");
//         arrayList.add("Sticker");

        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("ToolsList");
        if (arrayList.contains("Brush")) {
            arrayList.add("Eraser");
        } else {
            arrayList.remove("Eraser");
        }


       chooseImages();



        rvTools.setLayoutManager(new GridLayoutManager(AddPhoto.this, arrayList.size()));
        SelectedToolsAdapter selectedToolsAdapter = new SelectedToolsAdapter(arrayList, this, this);
        rvTools.setAdapter(selectedToolsAdapter);


        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFilters.setLayoutManager(llmFilters);
        FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
        rvFilters.setAdapter(mFilterViewAdapter);
    }

    private void chooseImages() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPhoto.this);
        View dialogview = LayoutInflater.from(this).inflate(R.layout.choosephoto_alertview, null, false);
        LinearLayout camera = dialogview.findViewById(R.id.alertview_camera);
        LinearLayout gallery = dialogview.findViewById(R.id.alertview_gallery);
        Button close=dialogview.findViewById(R.id.alertview_close_IV);
        builder.setView(dialogview);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                    alertDialog.dismiss();
                } else {
                    requestImageCapture();
                }

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
                    alertDialog.dismiss();
                } else {
                    requestOpenStorage();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buttonClicks() {

        PhotoEditorView = findViewById(R.id.photoEditorView);

        imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);

        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);

        rvTools = findViewById(R.id.rvTools);
        rvFilters = findViewById(R.id.rvFilters);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestImageCapture();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddPhoto.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(AddPhoto.this)
                            .setMessage("Please grant camera permission to use this feature ")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        }
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestOpenStorage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddPhoto.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(AddPhoto.this)
                            .setMessage("Please grant Storage permission to use this feature ")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        }
    }

    private void requestOpenStorage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        storageResultLauncher.launch(i);
    }

    private void requestImageCapture() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraResultLauncher.launch(intent);

//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraResultLauncher.launch(cameraIntent);

//        File imagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
//                File.separator + "camera.jpeg");
//        return FileProvider.getUriForFile(this,
//                "com.example.photoeditor.fileprovider",
//                imagePath);




        try {
//            File imgFile=File.createTempFile(filename,".jpg",storageDirectory);
//            currentPhotoPath=imgFile.getAbsolutePath();
//            File imgFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
//                File.separator + "camera.jpeg");

            File imgFile=File.createTempFile("cameraphoto",".png",getApplicationContext().getFilesDir());
            imageUri=FileProvider.getUriForFile(AddPhoto.this,
                    "com.example.photoeditor.fileprovider",imgFile);
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);

           
        } catch (Exception e)
        {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode)
       {
           case 1:
               if(resultCode==RESULT_OK)
               {
                   PhotoEditorView.getSource().setImageURI(imageUri);
               }
               else
               {
                   chooseImages();
               }
               break;
       }
    }

    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        photoEditor.clearAllViews();


                         Bitmap bitmap=BitmapFactory.decodeFile(currentPhotoPath);
                         PhotoEditorView.getSource().setImageBitmap(bitmap);


                    }
                    else
                    {
                        chooseImages();
                    }
                }
            });


    ActivityResultLauncher<Intent> storageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        photoEditor.clearAllViews();
                        try {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            PhotoEditorView.getSource().setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        chooseImages();
                    }
                }
            });


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgUndo:
                photoEditor.undo();
                break;
            case R.id.imgRedo:
                photoEditor.redo();
                break;
            case R.id.imgClose:
                   onBackPressed();
                break;
            case R.id.imgSave:
                SaveImage();
                break;

        }


    }



    private void SaveImage() {
          photoEditor.saveAsBitmap(new OnSaveBitmap() {
              @Override
              public void onBitmapReady(@Nullable Bitmap bitmap) {
                  try {
                      String dateTime;
                      Calendar calendar;
                      SimpleDateFormat simpleDateFormat;
                      calendar = Calendar.getInstance();
                      simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
                      dateTime = simpleDateFormat.format(calendar.getTime()).toString();


                      //saveImage(bitmap,dateTime);
                      saveImage(bitmap,dateTime);
                      Toast.makeText(AddPhoto.this, "Image Saved Sucessfully", Toast.LENGTH_SHORT).show();



                  } catch (Exception e)
                  {
                      Toast.makeText(AddPhoto.this, "Image Error", Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onFailure(@Nullable Exception e) {

              }
          });
    }

    private void saveImage(Bitmap bitmap, String Imgname) throws IOException {
        OutputStream fos ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = AddPhoto.this.getContentResolver();
            ContentValues contentValues = new ContentValues();

            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,Imgname+"-"+Imgname);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + IMAGES_FOLDER_NAME);

            Uri url = null;

            try {
                url = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                if (bitmap != null) {
                    fos = resolver.openOutputStream(url);
                    try {
                         bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                            Intent intent=new Intent(AddPhoto.this,FinalImage.class);
                            intent.putExtra("final",url);
                            startActivity(intent);
                            this.finish();

                    }finally {
                        fos.flush();
                        fos.close();
                    }

                }
            }catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + IMAGES_FOLDER_NAME;

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            Bitmap b=bitmap;
            File image = new File(imagesDir, Imgname + ".jpeg");
            fos = new FileOutputStream(image);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);


            fos.flush();
            fos.close();

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, Imgname + ".jpeg", null);
            Intent intent=new Intent(AddPhoto.this,FinalImage.class);
            intent.putExtra("final",Uri.parse(path));
            startActivity(intent);
            this.finish();

//            FileOutputStream outputStream=null;
//            File file=Environment.getExternalStorageDirectory();
//            File dir=new File(file.getAbsolutePath()+"/"+IMAGES_FOLDER_NAME);
//            dir.mkdirs();
//
//
//            File outfile=new File(dir,Imgname);
//            try{
//                outputStream=new FileOutputStream(outfile);
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//            Bitmap b=bitmap;
//            b.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//            try {
//                outputStream.flush();
//                outputStream.close();
//            }catch (Exception e)
//            {
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        }
    }

//    private void saveImage(Bitmap bitmap,String dateTime)
//    {
//        try {
//            String fileName = dateTime + ".jpg";
//
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+File.separator+IMAGES_FOLDER_NAME);
//                values.put(MediaStore.MediaColumns.IS_PENDING, 1);
//            } else {
//                //File directory = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM);
//                String directory = Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).toString() + File.separator + IMAGES_FOLDER_NAME;
//
//                File file = new File(directory, fileName);
//                values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
//            }
//
//            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//            try (OutputStream output = getContentResolver().openOutputStream(uri)) {
//                Bitmap bm = bitmap;
//                bm.compress(Bitmap.CompressFormat.JPEG, 100, output);
//            }
//        } catch (Exception e) {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }


//    private void saveImage(Bitmap bitmap, String dateTime) {
//        Uri images;
//        ContentResolver contentResolver=getContentResolver();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            images=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
//        }
//        else
//        {
//            images=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        }
//
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,dateTime+".jpg");
//        contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/*");
//        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator + IMAGES_FOLDER_NAME);
//        Uri uri=contentResolver.insert(images,contentValues);
//
//        try {
//            Bitmap b=bitmap;
//            OutputStream outputStream=contentResolver.openOutputStream(Objects.requireNonNull(uri));
//            b.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
//            Objects.requireNonNull(outputStream);
//
//            Toast.makeText(this, "Image saved Sucessfully", Toast.LENGTH_SHORT).show();
//
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(this, "Image Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

//    private void saveImage(Bitmap bitmap, String Imgname) throws IOException {
//
//        OutputStream fos ;
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                ContentResolver resolver = AddPhoto.this.getContentResolver();
//                ContentValues contentValues = new ContentValues();
//                if(Imgname.isEmpty())
//                {
//                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"strDate");
//                }
//                else {
//                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,Imgname);
//                }
//                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
//                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator + IMAGES_FOLDER_NAME);
//                Uri url = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//
//                fos = resolver.openOutputStream(Objects.requireNonNull(url));
//
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
//                Objects.requireNonNull(fos);
//
//                Toast.makeText(this, "Image Saved Sucessfully", Toast.LENGTH_SHORT).show();
//
//            }
//        }catch (Exception e)
//        {
//            Toast.makeText(this, "Image not Saved\n" +e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }



    private void stickerBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);

        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.emoji_bottomsheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        BottomSheetBehavior<View> bottomSheetBehavior;
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        RecyclerView emoji_RV = bottomSheetView.findViewById(R.id.emoji_RV);

        emoji_RV.setLayoutManager(new GridLayoutManager(this, 4));
        StickerAdapter stickerAdapter = new StickerAdapter(StickerList, this, this);
        emoji_RV.setAdapter(stickerAdapter);

    }

    private void emojiBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);

        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.emoji_bottomsheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        BottomSheetBehavior<View> bottomSheetBehavior;
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RecyclerView emoji_RV = bottomSheetView.findViewById(R.id.emoji_RV);

        emoji_RV.setLayoutManager(new GridLayoutManager(this, 4));
        EmojiAdapter emojiAdapter = new EmojiAdapter(EmojiList, this, this);
        emoji_RV.setAdapter(emojiAdapter);

    }

    private void textBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        BottomSheetBehavior<View> bottomSheetBehavior;
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.text_bottomsheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

        TextView doneBS = bottomSheetView.findViewById(R.id.textDone);
        enterTextBS = bottomSheetView.findViewById(R.id.textEnter);
        RecyclerView recyclerviewBS = bottomSheetView.findViewById(R.id.textRV);

        recyclerviewBS.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TextColorAdapter colorAdapter = new TextColorAdapter(colorArray, this, bottomSheetDialog, this);
        recyclerviewBS.setAdapter(colorAdapter);


        enterTextBS.setTextColor(TextColor);


        doneBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                String text = enterTextBS.getText().toString().trim();

                if (!text.isEmpty()) {
                    photoEditor.addText(text, TextColor);

                }
            }
        });

    }

    private void EraserBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.eraser_bottomsheet);
        bottomSheetDialog.show();

        photoEditor.brushEraser();

        SeekBar sbEraserSize = bottomSheetDialog.findViewById(R.id.sbEraserSize);
        if (EraserSize == 0) {
            sbEraserSize.setProgress(50);
        } else {
            sbEraserSize.setProgress(EraserSize);
        }
        sbEraserSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                EraserSize = i;
                photoEditor.setBrushSize(EraserSize);
                photoEditor.brushEraser();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bottomSheetDialog.dismiss();
            }
        });


    }

    private void BrushBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.brush_bottomsheet);

        SeekBar sbBrushSize = bottomSheetDialog.findViewById(R.id.sbBrushSize);
        SeekBar sbBrushOptacity = bottomSheetDialog.findViewById(R.id.sbBrushOptacity);
        RecyclerView rvBrushColor = bottomSheetDialog.findViewById(R.id.rvBrushColor);

        photoEditor.setBrushDrawingMode(true);

        if (BrushSize != 0) {
            sbBrushSize.setProgress(BrushSize);
        } else {
            sbBrushSize.setProgress(50);
        }


        if (BrushOptacity != 0) {
            sbBrushOptacity.setProgress(BrushOptacity);
        } else {
            sbBrushOptacity.setProgress(50);
        }


        sbBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                BrushSize = i;
                photoEditor.setBrushSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbBrushOptacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                BrushOptacity = i;
                photoEditor.setOpacity(i);

//                photoEditor.setShape(shapeBuilder.withShapeOpacity(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        rvBrushColor.setLayoutManager(new LinearLayoutManager(AddPhoto.this, LinearLayoutManager.HORIZONTAL, false));
        BrushColorAdapter brushColorAdapter = new BrushColorAdapter(colorArray, this, bottomSheetDialog, this);
        rvBrushColor.setAdapter(brushColorAdapter);


        bottomSheetDialog.show();
    }



    @Override
    public void toolsClick(String name) {

        switch (name) {
            case "Brush":
                photoEditor.setBrushDrawingMode(true);
                BrushBottomSheet();
                break;
            case "Eraser":
                EraserBottomSheet();
                break;
            case "Text":
                //photoEditor.addText(inputText, colorCode);
                textBottomSheet();
                break;
            case "Filter":
                rvTools.setVisibility(View.GONE);
                rvFilters.setVisibility(View.VISIBLE);
                break;
            case "Emoji":
//                photoEditor.addEmoji(String.valueOf(Character.toChars(0x1F601)));
                emojiBottomSheet();
                break;
            case "Stickers":
                stickerBottomSheet();
                break;

        }

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);

    }


    @Override
    public void BrushColor(int BrushColor) {
        TextColor = BrushColor;

        try {
            photoEditor.setBrushDrawingMode(true);
            photoEditor.setBrushColor(BrushColor);
        } catch (Exception e) {

        }
    }

    @Override
    public void Emoji(int num) {
        if (num != -1) {
            bottomSheetDialog.dismiss();
            photoEditor.addEmoji(String.valueOf(Character.toChars(EmojiList.get(num))));
        }

    }

    @Override
    public void sticker(int num) {
       if(num!=-1)
       {
           bottomSheetDialog.dismiss();
           Bitmap sticker=BitmapFactory.decodeResource(AddPhoto.this.getResources(),StickerList.get(num));
           photoEditor.addImage(sticker);
       }
    }

    private void saveAlert() {
        new AlertDialog.Builder(AddPhoto.this)
                .setMessage("Do you want to close without Saving ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddPhoto.super.onBackPressed();
                    }
                })
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void alertDialogForClose() {
        new AlertDialog.Builder(AddPhoto.this)
                .setMessage("Do you want to close without Saving")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddPhoto.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    @Override
    public void onBackPressed() {
         if(rvFilters.getVisibility()==View.VISIBLE)
        {
            rvFilters.setVisibility(View.GONE);
            rvTools.setVisibility(View.VISIBLE);
        }
         else if(!photoEditor.isCacheEmpty())
         {
             saveAlert();
         }
         else
         {
             super.onBackPressed();
         }

    }
}