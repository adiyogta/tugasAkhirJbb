package com.example.tugasakhirjbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText email, pass;
    private Button masuk,daftar;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private TextView  lupa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        masuk = (Button)findViewById(R.id.btnMasuk);
        daftar = (Button) findViewById(R.id.btnDaftar);
        lupa = (TextView) findViewById(R.id.lupapw);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, sign_up.class));
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String ps = pass.getText().toString().trim();

                if(TextUtils.isEmpty(em)){
                    email.setError("Email jangan kosong :(");
                    return;
                }

                if(TextUtils.isEmpty(ps)){
                    pass.setError("Password jangan kosong :(");
                    return;
                }

                if(ps.length()<6){
                    pass.setError("Password Min 6 karakter ya");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(em,ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(login.this, "Kamu Berhasil Masuk ^_^",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }else {
                            Toast.makeText(login.this,"Kamu Belum Berhasil Masuk :("+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(view.getContext());
                passResetDialog.setTitle("Reset Password");
                passResetDialog.setMessage("Masukkan Email Kamu Untuk Menerima Link Untuk Me-reset");
                passResetDialog.setView(resetMail);

                passResetDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(login.this, "Link Telah Dikirim Ke Email Anda", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this, "Link Tidak Terkirim ! "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passResetDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });

                passResetDialog.create().show();

            }
        });

    }
}
