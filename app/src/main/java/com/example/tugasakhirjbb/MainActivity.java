package com.example.tugasakhirjbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    ImageView add;

    Button mylist, myLove, myProfil;

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<barangdata> list;
    MyAdapter adapter;
    ValueEventListener eventListener;


    EditText searchs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (ImageView) findViewById(R.id.add);
        mylist = (Button) findViewById(R.id.list);
        myLove = (Button) findViewById(R.id.love);
        myProfil = (Button) findViewById(R.id.profil);

        searchs = (EditText)findViewById(R.id.searchEdt);

        recyclerView = (RecyclerView)findViewById(R.id.myRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        list = new ArrayList<>();

        final MyAdapter adapter = new MyAdapter(MainActivity.this,list);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Barang");
        eventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    barangdata barangdata2 = itemSnapshot.getValue(barangdata.class);
                    list.add(barangdata2);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, tambah.class));
            }
        });

        mylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, myList.class));
            }
        });

        myLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, love.class));
            }
        });

        myProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, profil.class));
            }
        });


        //search

//        searchs.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                filter(s.toString());
//
//            }
//
//            private void filter(String text) {
//
//                ArrayList<barangdata> filterList = new ArrayList<>();
//
//                for(barangdata item: list){
//
//                    if(item.getNamaB().toLowerCase().contains(text.toLowerCase())){
//
//                        filterList.add(item);
//
//                    }
//
//                }
//
//                adapter.filteredList(filterList);
//
//            }
//        });

    }
}
