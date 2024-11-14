package com.example.compras.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compras.R;
import com.example.compras.model.Produto;

import java.util.List;


public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder>{
    private List<Produto> listaProdutos;
    public ProdutoAdapter(List<Produto> listaprodutos){

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
        holder.nomeProduto.setText(produto.getNome());
        holder.precoProduto.setText(produto.getPreco().toString());
        holder.descricaoProduto.setText(produto.getDescricao());

        if (produto.getImagem() != null && !produto.getImagem().isEmpty()) {
            Bitmap bitmap = decodeBase64ToBitmap(produto.getImagem());
            holder.imagemImageView.setImageBitmap(bitmap);
        } else {
            holder.imagemImageView.setImageResource(R.drawable.icons8_instagram_50); // Placeholder image
        }
    }

    @Override
    public int getItemCount() {
        return this.listaProdutos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProduto;
        TextView precoProduto;
        TextView descricaoProduto;
        ImageView imagemImageView;
        public MyViewHolder(View itemView){
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.nomeProduto);
            precoProduto = itemView.findViewById(R.id.precoProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imagemImageView = itemView.findViewById(R.id.produtoImageView);
        }
    }

    // Método para decodificar base64 em bitmap
    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
