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
import com.example.compras.model.CarrinhoItem;

import java.util.List;


public class ItemCarrinhoAdapter extends RecyclerView.Adapter<ItemCarrinhoAdapter.MyViewHolder>{
    private List<CarrinhoItem> listaCarrinhoItem;
    public ItemCarrinhoAdapter(List<CarrinhoItem> listaCarrinho){

        this.listaCarrinhoItem = listaCarrinho;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Converte o XML para um objeto do tipo View
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_carrinho,parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemLista);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarrinhoItem carrinhoItem = this.listaCarrinhoItem.get(position);
        holder.nomeItemCarrinho.setText(carrinhoItem.getProduto().getNome());
        holder.precoItemCarrinho.setText(carrinhoItem.getPreco().toString());
        holder.descricaoItemCarrinho.setText(carrinhoItem.getProduto().getDescricao());

        if (carrinhoItem.getProduto().getImagem() != null && !carrinhoItem.getProduto().getImagem().isEmpty()) {
            Bitmap bitmap = decodeBase64ToBitmap(carrinhoItem.getProduto().getImagem());
            holder.imagemItemCarrinho.setImageBitmap(bitmap);
        } else {
            holder.imagemItemCarrinho.setImageResource(R.drawable.foto_instagram_50); // Placeholder image
        }
    }

    @Override
    public int getItemCount() {
        return this.listaCarrinhoItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeItemCarrinho;
        TextView precoItemCarrinho;
        TextView descricaoItemCarrinho;
        ImageView imagemItemCarrinho;
        public MyViewHolder(View itemView){
            super(itemView);
            nomeItemCarrinho = itemView.findViewById(R.id.nomeItemCarrinho);
            precoItemCarrinho = itemView.findViewById(R.id.precoItemCarrinho);
            descricaoItemCarrinho = itemView.findViewById(R.id.descricaoItemCarrinho);
            imagemItemCarrinho = itemView.findViewById(R.id.itemCarrinhoImageView);
        }
    }

    // Método para decodificar base64 em bitmap
    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
