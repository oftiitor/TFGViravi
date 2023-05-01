package com.example.tfgviravidam.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.ViewholderEventosBinding;
import com.example.tfgviravidam.fragmentsViravi.EventDetaillFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.EventoViewHolder> {
    private List<Evento> eventos;

    public PopularAdapter(List<Evento> eventos) {
        this.eventos = eventos;
        Log.i("as",eventos.toString());

    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_eventos, parent, false);
        return new EventoViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        // Obtener el evento correspondiente a esta posición

        Evento evento = eventos.get(position);

        // Rellenar la vista con los datos del evento correspondiente utilizando View Binding
        holder.binding.Nombre.setText(evento.getNombre());
        holder.binding.Categoria.setText(evento.getCategoria());
        holder.binding.Fechas.setText(evento.getFechaInicio()+"//"+evento.getFechaFin());
        Picasso.get().load(evento.getImagen()).resize(300, 200).centerCrop().into(holder.binding.Foto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Evento","sd");
                Log.i("Evento",evento.toString());
                // Abrir otro fragment
                Fragment fragment = new EventDetaillFragment();
                Bundle args = new Bundle();
                args.putParcelable("evento",evento);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out,R.anim.zoom_in, R.anim.zoom_out)
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        ViewholderEventosBinding binding;

        public EventoViewHolder(View itemView) {
            super(itemView);

            // Inicializar el binding
            binding = ViewholderEventosBinding.bind(itemView);
        }
    }
}


