package com.example.permisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class inicioSesion extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSION=111;

    private FirebaseAuth mAuth;
    private String TAG = "information";

    private TextView labelRegister;
    private EditText email;
    private EditText password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        solicitarPermisos();
        mAuth = FirebaseAuth.getInstance();
        findElements();
        listenersElements();
    }

    private void findElements(){
        labelRegister = findViewById(R.id.labelRegister);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
    }

    private void listenersElements(){
        labelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inicioSesion.this,registro.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setEnabled(false);
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                //logica del login con firebase cuando haya internet

                mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener(inicioSesion.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(inicioSesion.this, "Iniciaste sesion.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, user.toString());

                                    //limpiar campos
                                    email.setText("");
                                    password.setText("");

                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(inicioSesion.this, "Las credenciales no son correctas.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }

    private void solicitarPermisos() {
        int permisoStorage = ActivityCompat.checkSelfPermission(inicioSesion.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoCamara = ActivityCompat.checkSelfPermission(inicioSesion.this, Manifest.permission.CAMERA);

        if(permisoStorage!= PackageManager.PERMISSION_GRANTED || permisoCamara!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }
}