package com.imageview_circle.devix.imageview_circle;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageProfile;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageProfile = (ImageView) findViewById(R.id.profile_image);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String option1 = (String) getText(R.string.takePink);
                String option2 = getText(R.string.takeGallery).toString();
                builder.setTitle("Elige una Opcion");
                CharSequence[] array = {option1, option2};
                builder.setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tomarFoto();
                                break;
                            case 1:
                                tomarGaleria();
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void tomarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona una fotografia"), REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap imaBitmap = (Bitmap) bundle.get("data");
                    imageProfile.setImageBitmap(imaBitmap);
                }
                break;
            case REQUEST_IMAGE_GALLERY:
                Uri path = data.getData();
                imageProfile.setImageURI(path);
                break;
        }
    }
}

/*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap imaBitmap = (Bitmap) bundle.get("data");
            imageProfile.setImageBitmap(imaBitmap);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri path = data.getData();
            imageProfile.setImageURI(path);
        }

    }

*/