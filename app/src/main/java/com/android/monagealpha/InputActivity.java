package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class InputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button btnAdd,btnBack,btnKategori,btnDate,checkcircle,btnCalender;
    Kategori kategori;
    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnC;
    String input = "-IDR ",jumlahUang,jenis;
    TextView viewInput,viewKategori,viewDate,textTitle;
    ArrayList<String> jumlah;
    SwitchCompat switchbuttonin;
    String dateTime,date;
    int pemasukan=0,pengeluaran=0,saldo=Prevalent.currentOnlineUser.getSaldo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnC = findViewById(R.id.btnC);
        btnCalender = findViewById(R.id.btnCalender);
        checkcircle = findViewById(R.id.checkcircle);
        jumlah = new ArrayList<>();
        viewInput = findViewById(R.id.viewInput);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnKategori = findViewById(R.id.inputKategori);
        viewKategori = findViewById(R.id.viewKategori);
        kategori = new Kategori();
        viewInput.setText("-IDR ");
        viewDate = findViewById(R.id.viewDate);
        btnDate = findViewById(R.id.btnDate);
        textTitle = findViewById(R.id.textTitle);
        viewKategori.setText(kategori.getKategori());
        openKategori();openCalender();
        back();
        add0();add1();add2();add3();add4();add5();add6();add7();add8();add9();addC();

        checkcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        //switch buttton
        switchbuttonin= findViewById(R.id.switchbutton);
        switchbuttonin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchbuttonin.isChecked()){
                    textTitle.setText("Pemasukan");
                    input = "IDR ";
                    viewInput.setText("IDR ");
                    viewInput.setTextColor(Color.parseColor("#FDA300"));
                }else{
                    textTitle.setText("Pengeluaran");
                    input = "-IDR ";
                    viewInput.setTextColor(Color.parseColor("#D55252"));
                }
            }
        });
        openDate();

    }

    private void openDate(){
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }
    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date = dayOfMonth+"-"+month+"-"+year;
        viewDate.setText(date);

    }
    public  void addData(){

                jumlahUang = "";
                final DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference();
                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Users userData = dataSnapshot.child("Users").child(Prevalent.currentOnlineUser.getEmail()).getValue(Users.class);
                        Prevalent.currentOnlineUser = userData;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                for(int i = 0; i<jumlah.size();i++){
                    jumlahUang += jumlah.get(i);
                }
                if(switchbuttonin.isChecked()){
                    input = "IDR ";
                    jenis = "Pemasukan";
                    pemasukan += (Prevalent.currentOnlineUser.getPemasukan() + Integer.parseInt(jumlahUang));
                    saldo += Integer.parseInt(jumlahUang);

                }else {
                    input = "-IDR";
                    jenis = "Pengeluaran";
                    pengeluaran += (Prevalent.currentOnlineUser.getPengeluaran()+Integer.parseInt(jumlahUang));
                    saldo -= Integer.parseInt(jumlahUang);

                };
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("pemasukan",pemasukan);
                userdataMap.put("pengeluaran",pengeluaran);
                userdataMap.put("saldo",saldo);
                Rootref.child("Users").child(Prevalent.currentOnlineUser.getEmail()).child("History").child(date).updateChildren(userdataMap);
                if (switchbuttonin.isChecked()){
                    input="IDR ";
                    Rootref.child("Users").child(Prevalent.currentOnlineUser.getEmail()).child("pemasukan")
                            .setValue(pemasukan);
                }
                else {
                    input="-IDR ";
                    Rootref.child("Users").child(Prevalent.currentOnlineUser.getEmail()).child("pengeluaran")
                            .setValue(pengeluaran);
                }
                Rootref.child("Users").child(Prevalent.currentOnlineUser.getEmail()).child("saldo")
                        .setValue(saldo);
                viewInput.setText(input);
                Intent intent = new Intent(InputActivity.this,MainActivity.class);
                startActivity(intent);
    }
    public void back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void add1(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="1";
                jumlah.add("1");
                viewInput.setText(input);
            }
        });
    }
    public void add2(){
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="2";
                jumlah.add("2");
                viewInput.setText(input);
            }
        });
    }
    public void add3(){
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="3";
                jumlah.add("3");
                viewInput.setText(input);
            }
        });
    }
    public void add4(){
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="4";
                jumlah.add("4");
                viewInput.setText(input);
            }
        });
    }
    public void add5(){
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="5";
                jumlah.add("5");
                viewInput.setText(input);
            }
        });
    }public void add6(){
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="6";
                jumlah.add("6");
                viewInput.setText(input);
            }
        });
    }public void add7(){
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="7";
                jumlah.add("7");
                viewInput.setText(input);
            }
        });
    }public void add8(){
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="8";
                jumlah.add("8");
                viewInput.setText(input);
            }
        });
    }public void add9(){
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="9";
                jumlah.add("9");
                viewInput.setText(input);
            }
        });
    }public void add0(){
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input+="0";
                jumlah.add("0");
                viewInput.setText(input);
            }
        });
    }public void addC(){
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = "IDR ";
                jumlah.clear();
                viewInput.setText(input);
            }
        });
    }

    public void openKategori(){
        btnKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,KategoriActivity.class);
                startActivity(intent);
            }
        });
    }
    public void openCalender(){
        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }



}
