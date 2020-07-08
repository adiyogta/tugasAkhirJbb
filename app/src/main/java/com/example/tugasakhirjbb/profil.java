package com.example.tugasakhirjbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class profil extends AppCompatActivity  {

    ImageView add;

    Button mylist, myLove, myProfil;

    private ImageView ImageContainer;
    private TextView GetNama, GetEmail, GetNotelp;
    private FirebaseAuth auth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private String GetUserID;

    //Deklarasi Variable StorageReference
    private StorageReference reference;
    private FirebaseAuth firebaseAuth;

    //Kode permintaan untuk memilih metode pengambilan gamabr
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        GetNama = findViewById(R.id.nama);
        GetEmail = findViewById(R.id.email);
        GetNotelp = findViewById(R.id.notelp);
        auth = FirebaseAuth.getInstance();
        add = (ImageView) findViewById(R.id.add);
        mylist = (Button) findViewById(R.id.list);
        myLove = (Button) findViewById(R.id.love);
        myProfil = (Button) findViewById(R.id.homew);


        //Mendapatkan User ID dari akun yang terautentikasi
        FirebaseUser user = auth.getCurrentUser();
        GetUserID = user.getUid();

        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();

        getRefenence.child("data").child(GetUserID).child("users").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Mengambil value dari salah satu child, dan akan memicu data baru setiap kali diubah
                UserData userData = dataSnapshot.getValue(UserData.class);
                GetNama.setText(userData.getNama());
                GetEmail.setText(userData.getEmail());
                GetNotelp.setText(userData.getNotelp());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Digunakan untuk menangani kejadian Error
                Log.e("MyListData", "Error: ", databaseError.toException());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil.this, tambah.class));
            }
        });

        mylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil.this, myList.class));
            }
        });

        myLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil.this, love.class));
            }
        });

        myProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil.this, MainActivity.class));
            }
        });


    }


    public void keluar(View view) {
        firebaseAuth.signOut();
        startActivity(new Intent(profil.this, login.class));
        finish();
    }
}
