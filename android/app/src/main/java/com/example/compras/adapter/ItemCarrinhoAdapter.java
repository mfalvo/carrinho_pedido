package com.example.compras.adapter;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compras.R;
import com.example.compras.model.CarrinhoItem;
import com.example.compras.model.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public class ItemCarrinhoAdapter extends RecyclerView.Adapter<ItemCarrinhoAdapter.MyViewHolder>{
    private List<CarrinhoItem> listaCarrinhoItem;
    private List<Produto> listaProdutos;
    public ItemCarrinhoAdapter(List<CarrinhoItem> listaCarrinho, List<Produto> listaProdutos){

        this.listaCarrinhoItem = listaCarrinho;
        this.listaProdutos = listaProdutos;
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
        Long idDeBusca = carrinhoItem.getProduto().getId();
        Optional<Produto> produtoOptional = listaProdutos.stream()
                .filter(produto -> produto.getId().equals(idDeBusca))
                .findFirst();

        // Obtem imagem direto da lista de produtos do aplicativo
        if (produtoOptional.isPresent()) {
            Produto produtoEncontrado = produtoOptional.get();
            carrinhoItem.getProduto().setImagem(produtoEncontrado.getImagem());
        }

        holder.nomeItemCarrinho.setText(carrinhoItem.getProduto().getNome());
        holder.precoItemCarrinho.setText(carrinhoItem.getPreco().toString());
        holder.quantidadeItemCarrinho.setText(Integer.toString(carrinhoItem.getQuantidade()));
        BigDecimal subtotal = new BigDecimal(carrinhoItem.getQuantidade());
        subtotal = carrinhoItem.getPreco().multiply(new BigDecimal(carrinhoItem.getQuantidade()));
        holder.subtotalItemCarrinho.setText(subtotal.toString());
        holder.descricaoItemCarrinho.setText(carrinhoItem.getProduto().getDescricao());
        if (carrinhoItem.getProduto().getImagem() != null && !carrinhoItem.getProduto().getImagem().isEmpty()) {
            Bitmap bitmap = decodeBase64ToBitmap(carrinhoItem.getProduto().getImagem());
            holder.imagemItemCarrinho.setImageBitmap(bitmap);
        } else {
            holder.imagemItemCarrinho.setImageResource(R.drawable.foto_instagram_50); // Placeholder image
        }

        holder.selecaoItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mudarEstadoSelecaoItemCarrinho(v, holder, position); }
        });

        holder.incrementaItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { incdecItemCarrinho(1,v, holder, position); }
        });

        holder.decrementaItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { incdecItemCarrinho(-1,v, holder, position); }
        });

    }

    @Override
    public int getItemCount() {
        return this.listaCarrinhoItem.size();
    }

    public void mudarEstadoSelecaoItemCarrinho(View v, MyViewHolder holder, int position){
        if (this.listaCarrinhoItem.get(position).isSelecionado()){
            this.listaCarrinhoItem.get(position).setSelecionado(false);
            // Supondo que holder seja um ViewHolder dentro de um Adapter
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.toggle_off);
            holder.selecaoItemCarrinho.setImageDrawable(drawable);

        }else{
            this.listaCarrinhoItem.get(position).setSelecionado(true);
            Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.toggle_on);
            holder.selecaoItemCarrinho.setImageDrawable(drawable);
        }

    }

    public void incdecItemCarrinho(int delta, View v, MyViewHolder holder, int position){
        int quantItemCarrinho = this.listaCarrinhoItem.get(position).getQuantidade();
        if (quantItemCarrinho > 0 || delta>0) {
            quantItemCarrinho = quantItemCarrinho + delta;
            this.listaCarrinhoItem.get(position).setQuantidade(quantItemCarrinho);
            holder.quantidadeItemCarrinho.setText(Integer.toString(quantItemCarrinho));
        }
    }

    /////////////////////////////////////////////////////////////////////
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeItemCarrinho;
        ImageView selecaoItemCarrinho;
        ImageView incrementaItemCarrinho;
        TextView quantidadeItemCarrinho;
        ImageView decrementaItemCarrinho;
        TextView precoItemCarrinho;
        TextView descricaoItemCarrinho;
        ImageView imagemItemCarrinho;
        TextView subtotalItemCarrinho;
        public MyViewHolder(View itemView){
            super(itemView);
            selecaoItemCarrinho = itemView.findViewById(R.id.selecaoItemCarrinho);
            incrementaItemCarrinho = itemView.findViewById(R.id.incrementaItemCarrinho);
            quantidadeItemCarrinho = itemView.findViewById(R.id.quantidadeItemCarrinho);
            decrementaItemCarrinho = itemView.findViewById(R.id.decrementaItemCarrinho);
            nomeItemCarrinho = itemView.findViewById(R.id.nomeItemCarrinho);
            precoItemCarrinho = itemView.findViewById(R.id.precoItemCarrinho);
            descricaoItemCarrinho = itemView.findViewById(R.id.descricaoItemCarrinho);
            subtotalItemCarrinho = itemView.findViewById(R.id.subtotalItemCarrinho);
            imagemItemCarrinho = itemView.findViewById(R.id.itemCarrinhoImageView);
        }
    }

    // Método para decodificar base64 em bitmap
    public Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
