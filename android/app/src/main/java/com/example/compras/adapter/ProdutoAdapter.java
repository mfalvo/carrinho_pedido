package com.example.compras.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compras.R;
import com.example.compras.api.RetrofitClient;
import com.example.compras.controller.ClienteAPIController;
import com.example.compras.model.Cliente;
import com.example.compras.model.Produto;
import com.example.compras.utils.SharedPrefManager;
import com.example.compras.view.ListaProdutos;

import java.util.List;


public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>{
    private List<Produto> listaProdutos;
    private String emailCliente;

    private Context context;
    public ProdutoAdapter(Context context, String email, List<Produto> listaprodutos){
        this.context = context;
        this.emailCliente = email;
        this.listaProdutos = listaprodutos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Converte o XML para um objeto do tipo View
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_produto,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemLista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.idProduto.setText(Long.toString(produto.getId()));
        holder.nomeProduto.setText(produto.getNome());
        holder.precoProduto.setText(produto.getPreco().toString());
        holder.descricaoProduto.setText(produto.getDescricao());
        ImageView imgtest = holder.imageAdicionaNoCarrinho;
        int width = imgtest.getWidth();

        if (produto.getImagem() != null && !produto.getImagem().isEmpty()) {
            Bitmap bitmap = decodeBase64ToBitmap(produto.getImagem());
            holder.imagemImageView.setImageBitmap(bitmap);
        } else {
            holder.imagemImageView.setImageResource(R.drawable.foto_instagram_50); // Placeholder image
        }

        holder.imageAdicionaNoCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the view is null before accessing its properties
                if (v != null) {
                    adicionarProdutoNoCarrinho(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaProdutos.size();
    }

    public void adicionarProdutoNoCarrinho(View view, int position) {

        Produto produto = listaProdutos.get(position);
        long idProduto = produto.getId();
        adicionarNoCarrinho(view, this.emailCliente, idProduto, 1);
    }


    private void adicionarNoCarrinho(View view, String email, long idproduto, int quant){
        RetrofitClient retrofitClient;
        retrofitClient = new RetrofitClient();

        ClienteAPIController clienteAPIController = new ClienteAPIController(retrofitClient);
        clienteAPIController.adicionaProdutoNoCarrinho( email, idproduto, quant, new ClienteAPIController.ResponseCallback(){
            @Override
            public void onSuccess(Cliente cliente) {
                if (cliente != null) {
                    avisoLocal(view,"Produto adicionado com sucesso!");
                } else {
                    avisoLocal(view,"Nenhum produto encontrado!");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                avisoLocal(view,"Erro ao buscar produtos: " + t.getMessage());
                Log.e("ListaProdutos", "Erro ao buscar produtos", t);
            }
        });
    }

    private void avisoLocal(View view, String aviso) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setCancelable(false);
        alerta.setTitle("Aviso");
        alerta.setMessage(aviso);
        alerta.setNegativeButton("Ok", null);
        alerta.create().show();
    }


    // Método para decodificar base64 em bitmap
    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    ///// Classe MyViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView idProduto;
        TextView nomeProduto;
        TextView precoProduto;
        TextView descricaoProduto;
        ImageView imagemImageView;
        public ImageView imageAdicionaNoCarrinho;
        public MyViewHolder(View itemView){
            super(itemView);
            idProduto = itemView.findViewById(R.id.idProdutoXXX);
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            precoProduto = itemView.findViewById(R.id.precoProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imagemImageView = itemView.findViewById(R.id.produtoImageView);
            imageAdicionaNoCarrinho = itemView.findViewById(R.id.imageAdicionarCarrinho);
        }
    }


}
