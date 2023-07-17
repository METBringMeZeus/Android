package com.example.contactlist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.sql.Connection;

public class AddEditContact extends AppCompatActivity {
    private ImageView circle;
    private EditText Name,Phone,Email,Address;
    private MaterialButton Add;


    Uri imageUri;

    ActionBar actionBar;
    //permission constant
    private static final int CAMERA_PERMISSION_CODE=100;
    private static final int STORAGE_PERMISSION_CODE=200;
    private static final int IMAGE_FROM_GALLERY_CODE=300;
    private static final int IMAGE_FORM_CAMERA_CODE=400;
    //string array of permission
    private String[] camera_permission;
    private String[] storage_permission;

    private String[] internet_permission;




    String name , phone , mail , note;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        //initial permission
        internet_permission=new String[]{Manifest.permission.INTERNET};
        camera_permission=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storage_permission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        circle=findViewById(R.id.profileImg);
        Name=findViewById(R.id.Name_Input);
        Phone=findViewById(R.id.Phone_Input);
        Email=findViewById(R.id.Email_Input);
        Address=findViewById(R.id.adress_Input);
        Add=findViewById(R.id.AddBtn);

        //connect database


        //click fab
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               save();
            }
        });
        //click the face
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });
        //initial actionBar
        actionBar=getSupportActionBar();
        //set title actionbar
        actionBar.setTitle("Add Contact");
        //Back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void save() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        name= Name.getText().toString();
        phone=Phone.getText().toString();
        mail= Email.getText().toString();
        note=Address.getText().toString();
        if(!name.isEmpty()||!phone.isEmpty()||!mail.isEmpty()||!note.isEmpty()){
            Connection conn= DatabaseConnection.GetConnection();
            DatabaseConnection.AddContact(name,phone,mail,note);
            int rs = DatabaseConnection.rs;
            if(rs!=0){
                Toast.makeText(getApplicationContext(),"save complete",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"nothing to save",Toast.LENGTH_SHORT).show();
        }
    }

    private void showImagePickerDialog() {
        //option dialog
        String option[] ={"camera","Gallery"};
        //alert dialog builder
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setTitle("Chose an option");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    //camera selected
                    if(!checkPermissionCamera()){
                        RequestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }
                } else if (i==1) {
                    //storage selected
                    if(!checkStoragePermission()){
                        RequestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        }).create().show();
    }
    private void pickFromGallery() {
        Intent GalleryIntent =new Intent(Intent.ACTION_PICK);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent,IMAGE_FROM_GALLERY_CODE);
    }
    private void pickFromCamera() {
        ContentValues values= new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"image_title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"image_detail");
        imageUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_FORM_CAMERA_CODE);
    }


    //back button click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    //check camera permission
    private boolean checkPermissionCamera(){
        boolean resultOK = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean resultNo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return resultOK & resultNo;
    }
    // request camera permission
    private void RequestCameraPermission(){
        ActivityCompat.requestPermissions(this,camera_permission,CAMERA_PERMISSION_CODE);
    }
    //check storage permission
    private boolean checkStoragePermission(){
        boolean resultOk = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return resultOk;
    }
    // request storage permission
    private void RequestStoragePermission(){
        ActivityCompat.requestPermissions(this,storage_permission,STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"camera vs storage needed... ",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickFromGallery();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"storage needed... ",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_FROM_GALLERY_CODE){
                //pick image from gallery
                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode==IMAGE_FORM_CAMERA_CODE){
                //pick image from camera
                //crop image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddEditContact.this);
            } else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri=result.getUri();
                circle.setImageURI(imageUri);
            } else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(),"something wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

}