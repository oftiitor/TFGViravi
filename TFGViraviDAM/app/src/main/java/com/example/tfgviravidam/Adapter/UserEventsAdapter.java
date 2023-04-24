package com.example.tfgviravidam.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.UserCardBinding;
import com.example.tfgviravidam.databinding.ViewholderEventosBinding;
import com.example.tfgviravidam.databinding.ViewholderEventosProfileBinding;
import com.example.tfgviravidam.fragmentsViravi.ProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserEventsAdapter extends RecyclerView.Adapter<UserEventsAdapter.UserEventViewHolder> {
    private List<Evento> listaEvent;

    public UserEventsAdapter(List<Evento> listaEvent) {
        this.listaEvent = listaEvent;

    }

    @NonNull
    @Override
    public UserEventsAdapter.UserEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_eventos_profile, parent, false);
        return new UserEventsAdapter.UserEventViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull UserEventsAdapter.UserEventViewHolder holder, int position) {
        Evento e = listaEvent.get(position);

        holder.binding.Nombre.setText(e.getNombre());
        holder.binding.NumeroUsers.setText(e.getUsuariosApuntados().size() + " Usuarios apuntados");
        Picasso.get().load(e.getImagen()).resize(300, 200).centerCrop().into(holder.binding.FotoCard);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileFragment();
                Bundle args = new Bundle();
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEvent.size();
    }

    public class UserEventViewHolder extends RecyclerView.ViewHolder {

        ViewholderEventosProfileBinding binding;

        public UserEventViewHolder(View itemView) {
            super(itemView);

            // Inicializar el binding
            binding = ViewholderEventosProfileBinding.bind(itemView);
        }
    }
}