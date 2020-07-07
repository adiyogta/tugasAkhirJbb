package com.example.tugasakhirjbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class love extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<barangdata> myBarangList;
    barangdata mBarangdata;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

           recyclerView = (RecyclerView) findViewById(R.id.recycleLove);
           recyclerView.setLayoutManager( new LinearLayoutManager(this));

           final MyAdapter adapter = new MyAdapter(love.this, myBarangList);
           recyclerView.setAdapter(adapter);

           databaseReference = FirebaseDatabase.getInstance().getReference("Barangeve");

           eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   myBarangList.clear();
                   for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                       barangdata barangdata1 = itemSnapshot.getValue(barangdata.class);
                       myBarangList.add(barangdata1);

                   }

                   adapter.notifyDataSetChanged();

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });


    }

}
