package com.dogmeets;

import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dogmeets.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegister;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        auth = FirebaseAuth.getInstance();
        users=db.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)  {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            User user= new User();
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(password);

                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Пользователь добавлен", Toast.LENGTH_LONG)
                                                    .show();
                                    }
                            });
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
