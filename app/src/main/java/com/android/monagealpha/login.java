package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.monagealpha.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private EditText inputEmail,inputPassword;
    private Button btnLogin,btnRegister;
    private ProgressDialog loadingbar;
    private String parentDb = "Users";
    Users user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loadingbar = new ProgressDialog(this);
        btnRegister = findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }


    private void loginUser() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Write Your Email...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Write Your Password...",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Logging in");
            loadingbar.setMessage("Please wait!");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            allowAccess(email,password);
        }
    }

    private void allowAccess(final String email ,final String password) {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDb).child(email).exists()){
                    Users userData = dataSnapshot.child(parentDb).child(email).getValue(Users.class);
                    if(userData.getEmail().equals(email)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(login.this, "Logged In Successfull", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent = new Intent(login.this,MainActivity.class);
                            Prevalent.currentOnlineUser = userData;
                            startActivity(intent);
                        }else{
                            loadingbar.dismiss();
                            Toast.makeText(login.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(login.this, "Account with this "+email+" doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(login.this, "You must create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
