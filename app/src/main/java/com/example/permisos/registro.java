package com.example.permisos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class registro extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private String TAG = "information";

    private EditText name;
    private EditText email;
    private EditText password;
    private Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        mAuth = FirebaseAuth.getInstance();

        findElements();
        listernerElements();
    }

    private void findElements(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegistro = findViewById(R.id.btnRegistro);
    }

    private  void listernerElements(){
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegistro.setEnabled(false);

                String nameValue = name.getText().toString();
                String emailValue =email.getText().toString();
                String passwordValue = password.getText().toString();
                //logica para el registro cuando haya internet
                mAuth.createUserWithEmailAndPassword(emailValue,passwordValue)
                        .addOnCompleteListener(registro.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUser(user,nameValue);
                                    Toast.makeText(registro.this, "El usuario ha sido creado",
                                            Toast.LENGTH_SHORT).show();

                                    //limpiar campos
                                    name.setText("");
                                    email.setText("");
                                    password.setText("");
                                } else {
                                    Toast.makeText(registro.this, "El usuario no se creo, ocurrio un error",
                                            Toast.LENGTH_SHORT).show();
                                }
                                btnRegistro.setEnabled(true);
                            }
                        });
            }
        });
    }

    private void updateUser(FirebaseUser user ,String name){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });

    }
}
