package com.example.tfgviravidam.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.R;

import java.util.ArrayList;
import java.util.Date;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<Evento> eventosList;

    public PopularAdapter(ArrayList<Evento> eventos) {
        this.eventosList=eventos;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_eventos,parent,false);
        return new PopularAdapter.ViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(eventosList.get(position).getNombre());
        holder.categoria.setText(eventosList.get(position).getCategoria());


        String picUrl="";

        switch (position){
            case 0:{
                picUrl="a_ko_hong_a";
                break;

            }
            case 1:{
                picUrl="a_ko_hong_a";
                break;

            } case 2:{
                picUrl="a_ko_hong_a";
                break;

            }

        }
        int drawableResource= holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable", holder.itemView.getContext().getPackageName());

        /*Glide.with(holder.itemView.getContext())
                .load(drawableResource).
                into(holder.Foto);*/

    }

    @Override
    public int getItemCount() {
        return eventosList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Foto;

        TextView nombre, categoria, Fechas;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.Nombre);
            categoria = itemView.findViewById(R.id.Categoria);
            Fechas = itemView.findViewById(R.id.Fechas);
            Foto = itemView.findViewById(R.id.pic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
