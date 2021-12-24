package com.dogmeets;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dogmeets.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button btnSingIn, btnLinkToRegisterScreen;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    EditText inputEmail;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSingIn=findViewById(R.id.btnLogin);
        btnLinkToRegisterScreen=findViewById(R.id.btnLinkToRegisterScreen);

        auth = FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if ( !email.isEmpty() && !password.isEmpty()) {
                   auth.signInWithEmailAndPassword(email, password)
                           .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                               @Override
                               public void onSuccess(AuthResult authResult) {
                                   startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                                   finish();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(),
                                   "Ошибка авторизации", Toast.LENGTH_LONG)
                                   .show();
                       }
                   });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Введите данные!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }
}