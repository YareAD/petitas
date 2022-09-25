package com.example.permisos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class registro extends AppCompatActivity {


    private EditText name;
    private EditText email;
    private EditText password;
    private Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
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

                btnRegistro.setEnabled(true);
            }
        });

    }
}
