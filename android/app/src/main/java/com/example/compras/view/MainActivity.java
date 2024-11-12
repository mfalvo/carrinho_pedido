package com.example.compras.view;

import android.content.Intent;
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
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.model.Cliente;
import com.example.compras.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private EditText email_cliente;
    private EditText password_cliente ;


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

        this.email_cliente = findViewById(R.id.editTextTextEmailAddress);
        this.password_cliente = findViewById(R.id.editTextTextPassword);
    }

    // obtem dados de login
    public void getLogin(View view){


        String email_cliente = this.email_cliente.getText().toString();
        String password_cliente = this.password_cliente.getText().toString();


        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);

        clienteAPIController.getLoginCliente(email_cliente, password_cliente, new ClienteAPIController.ResponseCallback(){
            @Override
            public void onSuccess(Cliente cliente) {

                if (!(cliente == null)) {
                    // Compartilhamento do objeto Cliente no aplicativo
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(MainActivity.this);
                    sharedPrefManager.saveCliente(cliente);
                    //Chamada da interface de Produtos
                    Intent intent = new Intent(MainActivity.this, ListaProdutos.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Login");
                    String menssagem = "Usuário não encontrado! Verifique seu username e password.";
                    alerta.setMessage(menssagem);
                    alerta.setNegativeButton("Ok", null);
                    alerta.create().show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                //Log.e("UserList", "Erro ao buscar usuários", t);
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setCancelable(false);
                alerta.setTitle("Login");
                alerta.setMessage(t.toString());
                alerta.setNegativeButton("Ok",null);
                alerta.create().show();
            }
        });


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