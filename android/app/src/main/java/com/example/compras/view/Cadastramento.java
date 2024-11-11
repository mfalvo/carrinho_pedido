package com.example.compras.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

public class Cadastramento extends AppCompatActivity {

    // Define atributos relacionados com os elementos da interface do usuário
    // para acesso às informações dadas para o cadastro
    private ImageView imgFoto;
    private EditText emailCliente;
    private EditText nomeCliente;
    private EditText senhaCliente01;
    private EditText senhaCliente02;


    // Na criação da activity, os atributos recebem os objetos da interface que recebem dados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastramento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.imgFoto = findViewById(R.id.imgFotoCliente);
        this.emailCliente = findViewById(R.id.editEmailCliente);
        this.nomeCliente = findViewById(R.id.editNomeCliente);
        this.senhaCliente01 = findViewById(R.id.editPassword01);
        this.senhaCliente02 = findViewById(R.id.editPassword02);

    }

    // Executa Cadastramento de cliente
    public void getLogin(View view){


        String email_cliente = this.emailCliente.getText().toString();
        String nome_cliente = this.nomeCliente.getText().toString();
        String password01 = this.senhaCliente01.getText().toString();
        String password02 = this.senhaCliente02.getText().toString();

        if(password01.equals(password02)) {

            RetrofitClient retrofitClient = new RetrofitClient();
            ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);

            clienteAPIController.setCadastraCliente(email_cliente, nome_cliente, password01, new ClienteAPIController.ResponseCallback() {
                @Override
                public void onSuccess(Cliente cliente) {
                    //textView.setText(user_list.getSupport().getText());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastramento.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Login");
                    alerta.setMessage(cliente.toString());
                    alerta.setNegativeButton("Ok", null);
                    alerta.create().show();
                }

                @Override
                public void onFailure(Throwable t) {
                    //Log.e("UserList", "Erro ao buscar usuários", t);
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastramento.this);
                    alerta.setCancelable(false);
                    alerta.setTitle("Login");
                    alerta.setMessage(t.toString());
                    alerta.setNegativeButton("Falouu", null);
                    alerta.create().show();
                }
            });
        }
        else
        {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Cadastramento.this);
            alerta.setCancelable(false);
            alerta.setTitle("Cadastramento");
            alerta.setMessage("As senhas informadas não são idênticas!! Tente novamente!");
            alerta.setNegativeButton("Ok", null);
            alerta.create().show();
        }

    }
}