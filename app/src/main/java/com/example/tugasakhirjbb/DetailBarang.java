package com.example.tugasakhirjbb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailBarang extends AppCompatActivity {

    ImageView detailI;
    TextView nama,ukuran,kekurangan,lokasi,harga,keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        detailI = (ImageView)findViewById(R.id.detailimage);
        nama = (TextView) findViewById(R.id.namaDb);
        ukuran = (TextView) findViewById(R.id.ukuranDb);
        kekurangan = (TextView) findViewById(R.id.kekuranganDb);
        lokasi = (TextView) findViewById(R.id.lokasiDb);
        harga = (TextView) findViewById(R.id.hargaDb);
        keterangan = (TextView) findViewById(R.id.keteranganDb);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){
//            detailI.setImageResource(mBundle.getInt("Image"));
            nama.setText(mBundle.getString("Nama"));
            ukuran.setText(mBundle.getString("Ukuran"));
            kekurangan.setText(mBundle.getString("Kekurangan"));
            lokasi.setText(mBundle.getString("Lokasi"));
            harga.setText(mBundle.getString("Harga"));
            keterangan.setText(mBundle.getString("Keterangan"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(detailI);
        }
    }
}
