package com.android.monagealpha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText inputNama,inputEmail,inputPassword;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        inputNama = findViewById(R.id.inputNama);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loadingbar = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    public void createAccount(){
        String nama = inputNama.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(nama)){
            Toast.makeText(this,"Please Write Your Name...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Write Your Phone...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Write Your Password...",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar.setTitle("Create Account");
            loadingbar.setMessage("Please wait!");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatePhoneNumber(nama,email,password);
        }
    }

    private void ValidatePhoneNumber(final String nama, final String email, final String password) {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(email).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);
                    userdataMap.put("nama",nama);
                    userdataMap.put("pemasukan",0);
                    userdataMap.put("pengeluaran",0);
                    userdataMap.put("saldo",0);

                    Rootref.child("Users").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Your Account Created", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,login.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingbar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error,Try Again Later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(RegisterActivity.this,"This "+email+" Already exist ",Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try another phone number", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
