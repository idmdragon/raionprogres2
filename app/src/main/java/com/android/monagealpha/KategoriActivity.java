package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KategoriActivity extends AppCompatActivity  {

    Button btnMakanan;
    Kategori kategori;
    InputActivity inputActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        btnMakanan = findViewById(R.id.btnMakanan);
        setMakanan();


//        TableLayout tabLayout= (TableLayout) findViewById(R.id.tabLayout);
//        tabLayout.add(tabLayout.newTab().setText())

    }

    public void setMakanan(){
        btnMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this,InputActivity.class);
                startActivity(intent);
            }
        });
    }
}
