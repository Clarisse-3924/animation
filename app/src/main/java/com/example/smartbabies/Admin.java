package com.example.smartbabies;
import android.content.ContentResolver;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Handler;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.MimeTypeMap;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class Admin extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =1;
    private EditText Productname,Description,Price;
    private Button choose;
    private Button submit,view;
    private ImageView image;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private StorageTask muploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Productname=findViewById(R.id.productname);
        Description=findViewById(R.id.desc);
        Price=findViewById(R.id.price);
        choose=findViewById(R.id.button4);
        submit=findViewById(R.id.button5);
        view=findViewById(R.id.button3);

        image=findViewById(R.id.imageView4);
        mProgressBar=findViewById(R.id.progressBar4);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabase = FirebaseDatabase.getInstance().getReference("uploads");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,selling.class));

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(muploadTask != null && muploadTask.isInProgress()){
                    Toast.makeText(Admin.this,"upload in progress",Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

    }
    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                data.getData()!=null){
            mImageUri=data.getData();
            Glide.with(this).load(mImageUri).into(image);

        }

    }
    private String getFileExtension(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void uploadFile(){
        if(mImageUri !=null){
            StorageReference fileReference=mStorageRef.child(System.currentTimeMillis() + "." +getFileExtension(mImageUri));
            muploadTask =fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);

                                }

                            },500);
                            Toast.makeText(Admin.this,"Upload sucessful",Toast.LENGTH_LONG).show();
                            upload Upload = new upload(Productname.getText().toString().trim(),
                                    Description.getText().toString().trim(),Price.getText().toString(),
                                    taskSnapshot.getDownloadUrl().toString());

                            String UploadId=mDatabase.push().getKey();
                            mDatabase.child(UploadId).setValue(Upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Admin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress =(100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);


                        }
                    });
        }
        else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

}
