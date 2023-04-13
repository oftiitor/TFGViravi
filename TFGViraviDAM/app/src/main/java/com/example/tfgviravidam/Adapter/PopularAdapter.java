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
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.EventoViewHolder> {
    private List<Evento> eventos;

    public PopularAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el archivo XML de la vista de cada elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_eventos, parent, false);
        return new EventoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        // Obtener el evento correspondiente a esta posición
        Evento evento = eventos.get(position);
        holder.nombreEventoTextView.setText(evento.getNombre());
        holder.descripcionEventoTextView.setText(evento.getDescripcion());
        holder.usuarioCreadorTextView.setText(evento.getUsuarioCreador());
        // Cargar la imagen del evento usando Glide o alguna otra librería de carga de imágenes
        Glide.with(holder.itemView.getContext())
                .load(evento.getImagen())
                .into(holder.fotoEventoImageView);
        holder.ciudadEventoTextView.setText(evento.getCiudad());
        holder.categoriaEventoTextView.setText(evento.getCategoria());
        holder.fechaInicioTextView.setText(evento.getFechaInicio());
        holder.fechaFinTextView.setText(evento.getFechaFin());
        holder.usuariosApuntadosTextView.setText(String.valueOf(evento.getUsuariosApuntados()));
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    // Clase interna que representa la vista de cada elemento del RecyclerView
    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreEventoTextView;
        public TextView descripcionEventoTextView;
        public TextView usuarioCreadorTextView;
        public ImageView fotoEventoImageView;
        public TextView ciudadEventoTextView;
        public TextView categoriaEventoTextView;
        public TextView fechaInicioTextView;
        public TextView fechaFinTextView;
        public TextView usuariosApuntadosTextView;

        public EventoViewHolder(View itemView) {
            super(itemView);
            nombreEventoTextView = itemView.findViewById(R.id.Nombre);
            fotoEventoImageView = itemView.findViewById(R.id.Foto);
            categoriaEventoTextView = itemView.findViewById(R.id.Categoria);
            fechaInicioTextView = itemView.findViewById(R.id.Fechas);
            fechaFinTextView = itemView.findViewById(R.id.Fechas);
        }
    }
}
