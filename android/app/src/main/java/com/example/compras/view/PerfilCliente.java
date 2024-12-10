package com.example.compras.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.compras.R;
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.model.Cliente;
import com.example.compras.model.Produto;
import com.example.compras.utils.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PerfilCliente extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageViewFotoCliente;
    private EditText editUserNameCliente;
    private TextView textUserNameCliente;
    private EditText nomeCliente;
    private EditText senhaCliente1;
    private EditText senhaCliente2;
    private Cliente clienteLogado;
    private String currentPhotoPath;
    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        this.editUserNameCliente = findViewById(R.id.editTextUserNameCliente);
        this.textUserNameCliente = findViewById(R.id.textViewUserNameCliente);
        this.nomeCliente = findViewById(R.id.editTextNomeCliente);
        this.senhaCliente1 = findViewById(R.id.editTextSenha1);
        this.senhaCliente2 = findViewById(R.id.editTextSenha2);
        this.imageViewFotoCliente = findViewById(R.id.fotoPerfilCliente);
        // Initialize the ActivityResultLauncher
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        handleImageCapture();
                    }
                }
        );
        this.imageViewFotoCliente.setOnClickListener(v -> dispatchTakePictureIntent());
        SharedPrefManager sharedPrefManager = new SharedPrefManager(PerfilCliente.this);
        this.clienteLogado = sharedPrefManager.getCliente();
        if (this.clienteLogado != null) {
            this.editUserNameCliente.setEnabled(false);
            this.editUserNameCliente.setVisibility(View.INVISIBLE);
            this.nomeCliente.setText(this.clienteLogado.getNome());
            this.textUserNameCliente.setText(this.clienteLogado.getEmail());
            this.textUserNameCliente.setEnabled(true);
            this.textUserNameCliente.setVisibility(View.VISIBLE);
        }else
        {
            this.editUserNameCliente.setEnabled(true);
            this.editUserNameCliente.setVisibility(View.VISIBLE);
            this.textUserNameCliente.setEnabled(false);
            this.textUserNameCliente.setVisibility(View.INVISIBLE);
        }
    }


    public void sairPerfil(View view){
        finish();
    }


    public void salvaPerfil(View view) {

        Cliente clienteAtual = new Cliente();
        clienteAtual.setNome(this.nomeCliente.getText().toString());
        clienteAtual.setSenha(this.senhaCliente1.getText().toString());
        if (this.clienteLogado != null) {
            // Será uma alteração de cadastro de Cliente já logado
            if (senhasCompativeis()) {
                clienteAtual.setNome(this.nomeCliente.getText().toString());
                clienteAtual.setSenha(this.senhaCliente1.getText().toString());
                // TODO: capturar a imagem de imagem
                alterarCadastroCliente(clienteLogado.getEmail(), clienteAtual);
            } else {
                avisoLocal("O conteúdo dos campos senhas devem ser idênticos!\n Tente novamente!");
            }
        } else {   // Será o cadastramento de um novo Cliente
            if (this.editUserNameCliente.getText().toString().isEmpty()) {
                avisoLocal("Campo UserName deve conter um email válido!\n Tente novamente!");
            }else {
                if(senhasCompativeis()){
                    clienteAtual.setEmail(this.editUserNameCliente.getText().toString());
                    executarCadastroCliente(clienteAtual);
                }else {
                    avisoLocal("O conteúdo dos campos senhas devem ser idênticos!\n Tente novamente!");
                }
            }
        }
    }

    private boolean senhasCompativeis(){
        return this.senhaCliente1.getText().toString().equals(this.senhaCliente2.getText().toString())&&
                !this.senhaCliente1.getText().toString().isEmpty();
    }


    private void avisoLocal(String aviso){
        AlertDialog.Builder alerta = new AlertDialog.Builder(PerfilCliente.this);
        alerta.setCancelable(false);
        alerta.setTitle("Aviso");
        alerta.setMessage(aviso);
        alerta.setNegativeButton("Ok", null);
        alerta.create().show();
    }


    private void alterarCadastroCliente(String email, Cliente cliente){

        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();
        ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);
        clienteAPIController.alteraPerfilCliente(email, cliente, new ClienteAPIController.ResponseCallback(){
            @Override
            public void onSuccess(Cliente cliente) {
                if (cliente != null) {
                    // Compartilhamento do objeto Cliente atualizado no aplicativo
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(PerfilCliente.this);
                    sharedPrefManager.saveCliente(cliente);
                    finish();
                }
                else {
                    String menssagem = "Usuário não encontrado! Verifique seu username e password.";
                    avisoLocal(menssagem);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                //Log.e("UserList", "Erro ao buscar usuários", t);
                avisoLocal(t.toString());
            }
        });
    }

    private void executarCadastroCliente(Cliente cliente){
        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();
        ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);
        String email = cliente.getEmail().toString();
        clienteAPIController.cadastraCliente(cliente, new ClienteAPIController.ResponseCallback(){
            @Override
            public void onSuccess(Cliente cliente) {
                if (cliente != null) {
                    SharedPrefManager sharedPrefManager = new SharedPrefManager(PerfilCliente.this);
                    sharedPrefManager.saveCliente(cliente);
                    avisoLocal("Cadastro realizado com sucesso!!");
                    finish();
                }
                else {
                    avisoLocal("Houve problemas no cadastramento, tente novamente!!");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                //Log.e("UserList", "Erro ao buscar usuários", t);
                avisoLocal(t.toString());
            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.compras.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureLauncher.launch(takePictureIntent);
            }
        }
    }


    private void handleImageCapture() {
        // Get the dimensions of the View
        int targetW = imageViewFotoCliente.getWidth();
        int targetH = imageViewFotoCliente.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageViewFotoCliente.setImageBitmap(bitmap);
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



}