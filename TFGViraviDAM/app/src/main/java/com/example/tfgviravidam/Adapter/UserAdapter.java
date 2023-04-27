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

import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.UserCardBinding;
import com.example.tfgviravidam.fragmentsViravi.ProfileFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<Usuario> listaUser;

    public UserAdapter(List<Usuario> listaUser) {
        this.listaUser = listaUser;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // Obtener el evento correspondiente a esta posición
        Usuario user = listaUser.get(position);

        // Rellenar la vista con los datos del evento correspondiente utilizando View Binding
        holder.binding.tvUser.setText(user.getNombreUsuario());
        Picasso.get().load(user.getFotoPerfil()).resize(300, 200).centerCrop().into(holder.binding.ivFotoPerfil);

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
        return listaUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        UserCardBinding binding;

        public UserViewHolder(View itemView) {
            super(itemView);

            // Inicializar el binding
            binding = UserCardBinding.bind(itemView);
        }
    }
}