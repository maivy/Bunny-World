package edu.stanford.cs108.bunnyworld;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class CustomUploads extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 42;
    public CustomImages imageMap;
    public HashMap<String, Uri> customImages;
    public HashMap<String, BitmapDrawable> bitmapDrawables;
    private Uri requestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_uploads);
        imageMap = CustomImages.getInstance();
        customImages = imageMap.getImages();
        bitmapDrawables = imageMap.getBitmapDrawables();
    }

    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        String[] mimeTypes = {"image/jpg", "image/png", "image/gif"};
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //https://medium.com/@louis993546/how-to-ask-system-to-open-intent-to-select-jpg-and-png-only-on-android-i-e-no-gif-e0491af240bf
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }


    public void uploadSound(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                requestResult = resultData.getData();
                if(requestResult != null) {
                    LinearLayout nameImage = findViewById(R.id.nameImage);
                    nameImage.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void nameImage(View view) {
        try {
            BitmapDrawable imageDrawable;
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(requestResult, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            imageDrawable = new BitmapDrawable(getResources(), image);
            parcelFileDescriptor.close();
            EditText name = findViewById(R.id.imageName);
            customImages.put(name.getText().toString(), requestResult);
            bitmapDrawables.put(name.getText().toString(), imageDrawable);
            LinearLayout nameImage = findViewById(R.id.nameImage);
            nameImage.setVisibility(View.GONE);
            name.setText("");
            Toast toast = Toast.makeText(getApplicationContext(),"Image uploaded successfully",Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e) {
            Toast toast = Toast.makeText(getApplicationContext(),"Image could not load",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
