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


    ImageView add;

    Button mylist, myLove, myProfil;
    Button btnbrowse, btnupload;
    EditText namaBrg, ukuranBrg, kekuranganBrg, lokasiBrg, hargaBrg, keteranganBrg ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        add = (ImageView) findViewById(R.id.add);
        mylist = (Button) findViewById(R.id.list);
        myLove = (Button) findViewById(R.id.love);
        myProfil = (Button) findViewById(R.id.homewe);

        storageReference = FirebaseStorage.getInstance().getReference("Barang");
        databaseReference = FirebaseDatabase.getInstance().getReference("Barang");
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
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);    }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tambah.this, MainActivity.class));
            }
        });

        mylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tambah.this, myList.class));
            }
        });

        myLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tambah.this, love.class));
            }
        });

        myProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tambah.this, profil.class));
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

                            String nama = namaBrg.getText().toString().trim();
                            String ukuran = ukuranBrg.getText().toString().trim();
                            String kekurangan = kekuranganBrg.getText().toString().trim();
                            String lokasi = lokasiBrg.getText().toString().trim();
                            String harga = hargaBrg.getText().toString().trim();
                            String keterangan = keteranganBrg.getText().toString().trim();


                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            barangdata imageUploadInfo = new barangdata(nama,ukuran,kekurangan,lokasi,harga,keterangan, taskSnapshot.getUploadSessionUri().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(tambah.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

}
