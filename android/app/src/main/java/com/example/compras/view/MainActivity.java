package com.example.compras.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.compras.R;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.username = findViewById(R.id.editTextTextEmailAddress);
        this.password = findViewById(R.id.editTextTextPassword);
    }

    // obtem dados de login
    public void realizarLogin(View view){


        String email_user = this.username.getText().toString();
        String password_user = this.password.getText().toString();


    }

    public void recuperarSenha(View view) {
        // TODO: implementar rotina de recuperação de senha
        Integer X = 100;
        Integer Y = 200;
        Integer Z = X + Y;
    }

    public void realizarCadastramento(View view){
        // TODO: implementar rotina de cadastramento de novo usuário
        Integer X = 100;
        Integer Y = 200;
        Integer Z = X + Y;
    }
}