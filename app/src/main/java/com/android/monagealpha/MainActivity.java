package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {



    TextView viewPemasukan,viewSaldo,viewPengeluaran;
    Button btnPlus;
    int jumlahPemasukan,jumlahPengeluaran,jumlahSaldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPemasukan = findViewById(R.id.viewPemasukan);
        viewSaldo = findViewById(R.id.viewSaldo);

        viewPengeluaran = findViewById(R.id.viewPengeluaran);
        final DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users userData = dataSnapshot.child("Users").child(Prevalent.currentOnlineUser.getEmail()).getValue(Users.class);
                Prevalent.currentOnlineUser = userData;
                viewPemasukan.setText(String.valueOf(Prevalent.currentOnlineUser.getPemasukan()));
                viewPengeluaran.setText(String.valueOf(Prevalent.currentOnlineUser.getPengeluaran()));
                viewSaldo.setText(String.valueOf(Prevalent.currentOnlineUser.getSaldo()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        openInput();

        //Buat Segmentnya

        BottomNavigationView bottomnav = findViewById(R.id.bottom_nav);
        bottomnav.getMenu().findItem(R.id.input).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
                return true;
            }
            });


        bottomnav.setOnNavigationItemSelectedListener(navListener);




    }



    //Lanjutan Buat Segment

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()) {
                        case R.id.laporan:
                            selectFragment = new laporan();
                            break;

                        case R.id.history:
                            selectFragment = new history();
                            break;


                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, selectFragment)
                            .commit();
                    return true;
                }

            };

////    public void openInput(){
////        btnPlus.setOnClickListener(new View.OnClickListener() {
////            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,InputActivity.class);
//                startActivity(intent);
//            }
//        });
    }

