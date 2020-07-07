package com.example.tugasakhirjbb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class tambah extends AppCompatActivity {

    Button btnbrowse, btnupload;
    EditText namaBrg, ukuranBrg, kekuranganBrg, lokasiBrg, hargaBrg, keteranganBrg ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    StorageReference storageReferenceku;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        storageReference = FirebaseStorage.getInstance().getReference("users");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        btnbrowse = (Button)findViewById(R.id.pilihBtn);
        btnupload= (Button)findViewById(R.id.tambahBtn);
        namaBrg = (EditText)findViewById(R.id.namaB);
        ukuranBrg = (EditText)findViewById(R.id.ukuranB);
        kekuranganBrg = (EditText)findViewById(R.id.kekuranganB);
        lokasiBrg = (EditText)findViewById(R.id.lokasiB);
        hargaBrg = (EditText)findViewById(R.id.hargaB);
        keteranganBrg = (EditText)findViewById(R.id.keteranganB);
        imgview = (ImageView)findViewById(R.id.iv);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String namaBr = namaBrg.getText().toString().trim();
                            String ukuranBr = namaBrg.getText().toString().trim();
                            String kekuranganBr = namaBrg.getText().toString().trim();
                            String lokasiBr = namaBrg.getText().toString().trim();
                            String hargaBr = namaBrg.getText().toString().trim();
                            String keteranganBr = namaBrg.getText().toString().trim();

                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            barangdata imageUploadInfo = new barangdata (namaBr,ukuranBr,kekuranganBr,lokasiBr,hargaBr,keteranganBr, taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child("nama").setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(tambah.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}
